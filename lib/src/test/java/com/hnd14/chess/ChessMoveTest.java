package com.hnd14.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hnd14.chess.position.File;
import com.hnd14.chess.position.Rank;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.InvalidPositionTypeException;

public class ChessMoveTest {
    private ChessPosition c3 = ChessPosition.builder().file(File.C).rank(Rank.THIRD).build();
    private ChessPosition c4 = ChessPosition.builder().file(File.C).rank(Rank.FOURTH).build();
    private Piece piece = new Piece(null, null);

    @Test
    void testCreateMove() {
        assertThrows(InvalidPositionTypeException.class, 
            () -> ChessMove.createMove(c3, c3, piece));
        assertThrows(InvalidPositionTypeException.class, 
            () -> ChessMove.createMove(new Position() {}, c3, piece));
        assertThrows(InvalidPositionTypeException.class, 
            () -> ChessMove.createMove(c3, new Position() {}, piece));
        assertEquals(ChessMove.builder().start(c3).end(c4).piece(piece).build(), ChessMove.createMove(c3, c4, piece));
    }

}
