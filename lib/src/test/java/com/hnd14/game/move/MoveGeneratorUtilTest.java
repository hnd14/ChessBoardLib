package com.hnd14.game.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.hnd14.game.GameState;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.NoPieceAtPositionException;
import com.hnd14.game.exception.PositionCannotBeTransformedException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.position.PositionSingleTransformer;

public class MoveGeneratorUtilTest extends SampleGameElementsConfig {
    @Test 
    void testApplyTransformer() {
        GameState gameState = new SampleGameState(Map.of());
        Position pos1 = new SamplePosition(2, 2); 
        Position pos2 = new SamplePosition(2, 9);
        Position pos3 = new SamplePosition(0, 0);
        Position pos4 = new Position() {};
        PositionSingleTransformer transformer = new PositionSingleTransformer() {
            public Position transform(Position position) {
                if (position instanceof SamplePosition castedPosition)
                    return new SamplePosition(castedPosition.x(), castedPosition.y() + 1); 
                throw new PositionCannotBeTransformedException(null);
            }
        };

        // Return the transformed position when possible
        assertEquals(transformer.transform(pos1), MoveGeneratorUtil.applyTransformer(transformer, gameState, pos1));
        
        // Return null when the transformed position is out of bound
        assertFalse(gameState.hasPosition(transformer.transform(pos2)));
        assertNull(MoveGeneratorUtil.applyTransformer(transformer, gameState, pos2));
        
        // Return null when the initial position is out of bound
        assertFalse(gameState.hasPosition(pos3));
        assertNull(MoveGeneratorUtil.applyTransformer(transformer, gameState, pos3));
        
        // Return null when position cannot be transformed
        assertThrows(PositionCannotBeTransformedException.class, () -> transformer.transform(pos4));
        assertNull(MoveGeneratorUtil.applyTransformer(transformer, gameState, pos4));
    }
    @Test
    void testGetPerformingPiece() {
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,3), new Piece(null, null)));
        Position pos1 = new SamplePosition(2, 3); 
        Position pos2 = new SamplePosition(2, 2);
        Position pos3 = new SamplePosition(0, 0);

        assertEquals(new Piece(null, null), MoveGeneratorUtil.getPerformingPiece(gameState, pos1));
        
        assertThrows(NoPieceAtPositionException.class, 
        () -> MoveGeneratorUtil.getPerformingPiece(gameState, pos2));
        
        assertThrows(PositionNotExistsException.class, 
        () -> MoveGeneratorUtil.getPerformingPiece(gameState, pos3));
    }
}
