package com.hnd14.game.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.hnd14.game.GameState;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.PositionCannotBeTransformedException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.position.PositionSingleTransformer;

class MoveAndAttackMoveGeneratorTest extends SampleGameElementsConfig {
    PositionSingleTransformer transformer = new PositionSingleTransformer() {
        public Position transform(Position position) {
            if (position instanceof SamplePosition castedPosition)
                return new SamplePosition(castedPosition.x(), castedPosition.y() + 1); 
            throw new PositionCannotBeTransformedException(null);
        }
    };

    MoveAndAttackMoveGenerator moveGenerator = new MoveAndAttackMoveGenerator(transformer, SampleMove::new) {
        @Override
        protected boolean requirements(GameState gameState, Position start, Position target, Piece performingPiece) {
            return !gameState.isOccupied(target);
        }
    };

    @Test 
    void testCreateMoveFromPositionOutsideOfGameState() {
        Piece piece = new Piece(null, SampleSide.ONE);
        Position start = new SamplePosition(0, 0);
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,2), piece));

        assertThrows(PositionNotExistsException.class, 
            () -> moveGenerator.generateMove(gameState, start, piece));
    }

    @Test
    void testGenerateMove() {
        SamplePosition pos1 = new SamplePosition(2, 3); 
        SamplePosition pos2 = new SamplePosition(2, 2);
        SamplePosition pos3 = new SamplePosition(2, 9);
        Piece piece = new Piece(null, SampleSide.ONE);
        GameState gameState = new SampleGameState(
            Map.of(pos1, piece,
                    pos2, piece,
                    pos3, piece));

        var result1 = moveGenerator.generateMove(gameState, pos1, piece);
        var result2 = moveGenerator.generateMove(gameState, pos2, piece);
        var result3 = moveGenerator.generateMove(gameState, pos3, piece);
        var expectedMove1 = new SampleMove(pos1, new SamplePosition(2, 4), piece);

        // Can be moved
        assertEquals(1, result1.size());
        assertTrue(result1.contains(expectedMove1));

        // Not pass requirements
        assertEquals(0, result2.size());
        
        // Try moving out of bound
        assertEquals(0, result3.size());
    }
}
