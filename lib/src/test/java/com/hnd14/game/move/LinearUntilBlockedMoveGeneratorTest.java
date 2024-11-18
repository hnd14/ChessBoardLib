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
import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.NoPieceAtPositionException;
import com.hnd14.game.exception.PositionCannotBeTransformedException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.piece.PieceTypeManager;
import com.hnd14.game.piece.Side;
import com.hnd14.game.position.PositionSingleTransformer;

public class LinearUntilBlockedMoveGeneratorTest {
    record SamplePosition(int x, int y) implements Position{
    }



    public record SampleMove(Position start, Position end, Piece piece) implements Move {
    }

    class SampleGameState implements GameState {
        private Map<SamplePosition, Piece> state;

        public SampleGameState(Map<SamplePosition, Piece> state) {
            this.state = state;
        }

        @Override
        public boolean hasPosition(Position position) {
            return (position instanceof SamplePosition samplePosition) 
                && samplePosition.x() < 10 && samplePosition.x() > 0
                && samplePosition.y() < 10 && samplePosition.y() > 0;
        }

        @Override
        public boolean isOccupied(Position position) {
            return state.containsKey(position);
        }

        @Override
        public Piece getPiece(Position position) {
            return state.get(position);
        }
    }
    public enum SampleSide implements Side {
        ONE, TWO;

        @Override
        public boolean isAllyWith(Side side) {
            return this.equals(side);
        }
        
    }


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
    void testGenerateMoveOnEmptySpace() {
        assertThrows(NoPieceAtPositionException.class, 
        () -> moveGenerator.generateMove(new SampleGameState(Map.of()), new SamplePosition(2, 2)));
    }

    @Test
    void testGenerateMoveOnPositionOutOfBoard() {
        
        assertThrows(PositionNotExistsException.class, 
        () -> moveGenerator.generateMove(new SampleGameState(Map.of()), new SamplePosition(0, 0)));
    }

    @Test 
    void testUnobstructed() {
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,2), new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE)));

        List<SamplePosition> expectedEnd = List.of(new SamplePosition(2,3),
            new SamplePosition(2,4),
            new SamplePosition(2,5),
            new SamplePosition(2,6),
            new SamplePosition(2,7),
            new SamplePosition(2,8),
            new SamplePosition(2,9));
        var result = moveGenerator.generateMove(gameState, new SamplePosition(2, 2));

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
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,2), new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE),
                    new SamplePosition(2, 7), new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE)));

        List<SamplePosition> expectedEnd = List.of(new SamplePosition(2,3),
            new SamplePosition(2,4),
            new SamplePosition(2,5),
            new SamplePosition(2,6));
        var result = moveGenerator.generateMove(gameState, new SamplePosition(2, 2));
        
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
        GameState gameState = new SampleGameState(
            Map.of(new SamplePosition(2,2), new Piece(pieceTypeManager.fromString("A"), SampleSide.ONE),
                    new SamplePosition(2, 7), new Piece(pieceTypeManager.fromString("A"), SampleSide.TWO)));

        List<SamplePosition> expectedEnd = List.of(new SamplePosition(2,3),
            new SamplePosition(2,4),
            new SamplePosition(2,5),
            new SamplePosition(2,6),
            new SamplePosition(2,7));
        var result = moveGenerator.generateMove(gameState, new SamplePosition(2, 2));

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
