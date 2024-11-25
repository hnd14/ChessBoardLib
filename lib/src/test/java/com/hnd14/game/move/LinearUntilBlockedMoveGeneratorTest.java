package com.hnd14.game.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hnd14.game.GameState;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.PositionCannotBeTransformedException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.piece.PieceTypeManager;
import com.hnd14.game.position.PositionSingleTransformer;

class LinearUntilBlockedMoveGeneratorTest extends SampleGameElementsConfig{

    static PieceTypeManager pieceTypeManager = new PieceTypeManager();
    static LinearUntilBlockedMoveGenerator moveGenerator;

    @BeforeAll
    static void setup() {
        pieceTypeManager.register("A");
        pieceTypeManager.register("B");
        PositionSingleTransformer transformer = new PositionSingleTransformer() {
            public Position transform(Position position) {
                if (position instanceof SamplePosition castedPosition)
                    return new SamplePosition(castedPosition.x(), castedPosition.y() + 1); 
                throw new PositionCannotBeTransformedException(null);
            }
        };
        moveGenerator = new LinearUntilBlockedMoveGenerator(transformer, SampleMove::new);
    }

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
    void testUnobstructed() {
        Piece piece = new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE);
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,2), piece));

        List<SamplePosition> expectedEnd = List.of(new SamplePosition(2,3),
            new SamplePosition(2,4),
            new SamplePosition(2,5),
            new SamplePosition(2,6),
            new SamplePosition(2,7),
            new SamplePosition(2,8),
            new SamplePosition(2,9));
        var result = moveGenerator.generateMove(gameState, new SamplePosition(2, 2), piece);

        assertEquals(expectedEnd.size(), result.size());
        assertTrue(result.stream() 
        .map(move -> {
            assertInstanceOf(SampleMove.class, move);
            return (SampleMove) move;
        })
        .map(move -> {
            assertEquals(new SamplePosition(2, 2), move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE), move.piece());
            return move.end();
        }).allMatch(expectedEnd::contains));

    }

    @Test 
    void testBlockedByAlly() {
        Piece piece = new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE);
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,2), piece,
                    new SamplePosition(2, 7), new Piece(pieceTypeManager.fromString("B"), SampleSide.ONE)));

        List<SamplePosition> expectedEnd = List.of(new SamplePosition(2,3),
            new SamplePosition(2,4),
            new SamplePosition(2,5),
            new SamplePosition(2,6));
        var result = moveGenerator.generateMove(gameState, new SamplePosition(2, 2),piece);
        
        assertEquals(expectedEnd.size(), result.size());
        assertTrue(result.stream() 
        .map(move -> {
            assertInstanceOf(SampleMove.class, move);
            return (SampleMove) move;
        })
        .map(move -> {
            assertEquals(new SamplePosition(2, 2), move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE), move.piece());
            return move.end();
        }).allMatch(expectedEnd::contains));
    }

    @Test 
    void testBlockedByOpponent() {
        Piece piece = new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE);
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,2), piece,
                    new SamplePosition(2, 7), new Piece(pieceTypeManager.fromString("A"), SampleSide.TWO)));

        List<SamplePosition> expectedEnd = List.of(new SamplePosition(2,3),
            new SamplePosition(2,4),
            new SamplePosition(2,5),
            new SamplePosition(2,6),
            new SamplePosition(2,7));
        var result = moveGenerator.generateMove(gameState, new SamplePosition(2, 2), piece);

        assertEquals(expectedEnd.size(), result.size());
        assertTrue(result.stream() 
        .map(move -> {
            assertInstanceOf(SampleMove.class, move);
            return (SampleMove) move;
        })
        .map(move -> {
            assertEquals(new SamplePosition(2, 2), move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE), move.piece());
            return move.end();
        }).allMatch(expectedEnd::contains));
    }
}
