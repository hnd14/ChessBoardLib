package com.hnd14.game.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hnd14.chess.exception.PieceTypeDoesNotExistException;

class PieceTypeManagerTest {
    @Test
    void testFromStringWithouthRegister () {
        PieceTypeManager pieceTypeManager = new PieceTypeManager();
        assertThrows(PieceTypeDoesNotExistException.class, () -> pieceTypeManager.fromString("Bishop"));
    }

    @Test 
    void testRegisterAndCreate() {
        PieceTypeManager pieceTypeManager = new PieceTypeManager();
        pieceTypeManager.register("Bishop");

        PieceTypeManager.PieceType result = pieceTypeManager.fromString("Bishop");

        // Assert correct piecetype
        assertEquals("Bishop", result.getName());
        // Assert fromString return the correct 
        assertEquals(result, pieceTypeManager.fromString("Bishop"));
    }

    @Test
    void testRegisterAliasForNotExistType() {
        PieceTypeManager pieceTypeManager = new PieceTypeManager();
        assertThrows(PieceTypeDoesNotExistException.class, 
            () -> pieceTypeManager.registerAlias("BISHOP", "Bishop"));
    }

    
    @Test 
    void testRegisterAliasCorrectly() {
        PieceTypeManager pieceTypeManager = new PieceTypeManager();
        pieceTypeManager.register("Bishop");
        pieceTypeManager.registerAlias("BISHOP", "Bishop");
        PieceTypeManager.PieceType expected = pieceTypeManager.fromString("Bishop");

        PieceTypeManager.PieceType result = pieceTypeManager.fromString("BISHOP");

        // Assert fromString return the correct 
        assertEquals(expected, result);
    }
}
