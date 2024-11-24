package com.hnd14.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.hnd14.chess.ChessPosition;
import com.hnd14.game.Position;
import com.hnd14.game.exception.PositionCannotBeTransformedException;

class SimpleChessPositionTransformerTest {
    private SimpleChessPositionTransformer positiveTransformer = SimpleChessPositionTransformer.builder()
                                                                    .fileValue(1)
                                                                    .rankValue(1)
                                                                    .build();

    
    private SimpleChessPositionTransformer negativeTransformer = SimpleChessPositionTransformer.builder()
                                                                    .fileValue(-1)
                                                                    .rankValue(-1)
                                                                    .build();


    private ChessPosition a5 = new ChessPosition(Rank.FIFTH, File.A);
    private ChessPosition c1 = new ChessPosition(Rank.FIRST, File.C);
    private ChessPosition c5 = new ChessPosition(Rank.FIFTH, File.C);
    private ChessPosition c8 = new ChessPosition(Rank.EIGHTH, File.C);
    private ChessPosition h5 = new ChessPosition(Rank.FIFTH, File.H);
    private Position other = new Position() {};

    @Test
    void testPositiveTransform() {
        // Transformable
        assertEquals(new ChessPosition(Rank.SIXTH, File.D), 
            positiveTransformer.transform(c5));
        // Cannot be transform
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> positiveTransformer.transform(h5));
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> positiveTransformer.transform(c8));
    }

    @Test
    void testNegativeTransform() {
        // Transformable
        assertEquals(new ChessPosition(Rank.FOURTH, File.B), 
            negativeTransformer.transform(c5));
        // Cannot be transformed
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> negativeTransformer.transform(a5));
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> negativeTransformer.transform(c1));
    }

    @Test 
    void testTransformInvalidPositionType () {
        assertThrows(PositionCannotBeTransformedException.class, () -> positiveTransformer.transform(other));
    }
}
