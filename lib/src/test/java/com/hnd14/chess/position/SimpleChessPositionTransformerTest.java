package com.hnd14.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.hnd14.chess.ChessPosition;
import com.hnd14.game.Position;
import com.hnd14.game.exception.PositionCannotBeTransformedException;

public class SimpleChessPositionTransformerTest {
    private SimpleChessPositionTransformer positiveTransformer = SimpleChessPositionTransformer.builder()
                                                                    .fileValue(1)
                                                                    .rankValue(1)
                                                                    .build();

    
    private SimpleChessPositionTransformer negativeTransformer = SimpleChessPositionTransformer.builder()
                                                                    .fileValue(-1)
                                                                    .rankValue(-1)
                                                                    .build();
    @Test
    void testPositiveTransform() {
        // Transformable
        assertEquals(new ChessPosition(Rank.SIXTH, File.D), 
            positiveTransformer.transform(new ChessPosition(Rank.FIFTH, File.C)));
        // Cannot be transform
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> positiveTransformer.transform(new ChessPosition(Rank.FIFTH, File.H)));
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> positiveTransformer.transform(new ChessPosition(Rank.EIGHTH, File.C)));
    }

    @Test
    void testNegativeTransform() {
        // Transformable
        assertEquals(new ChessPosition(Rank.FOURTH, File.B), 
            negativeTransformer.transform(new ChessPosition(Rank.FIFTH, File.C)));
        // Cannot be transformed
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> negativeTransformer.transform(new ChessPosition(Rank.FIFTH, File.A)));
        assertThrows(PositionCannotBeTransformedException.class, 
            () -> negativeTransformer.transform(new ChessPosition(Rank.FIRST, File.C)));
    }

    @Test 
    void testTransformInvalidPositionType () {
        assertThrows(PositionCannotBeTransformedException.class, () -> positiveTransformer.transform(new Position() {}));
    }
}
