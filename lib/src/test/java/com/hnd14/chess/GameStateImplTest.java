package com.hnd14.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hnd14.chess.position.File;
import com.hnd14.chess.position.Rank;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.piece.PieceTypeManager;
import com.hnd14.chess.piece.ChessSide;

class ChessGameStateImplTest {
    static PieceTypeManager pieceTypeManager = new PieceTypeManager();
    Position c3 = new ChessPosition(Rank.THIRD, File.C);
    Position d4 = new ChessPosition(Rank.FOURTH, File.D);
    Position h8 = new ChessPosition(Rank.EIGHTH, File.H);
    ChessGameStateImpl gameState;
    @BeforeAll
    static void setupBeforeAll () {
        pieceTypeManager.register("Bishop");
        pieceTypeManager.register("Knight");
        pieceTypeManager.registerAlias("B", "Bishop");
        pieceTypeManager.registerAlias("N", "Knight");
    } 

    @BeforeEach
    void setup() {
        gameState = ChessGameStateImpl
            .builder()
            .addPiece(new Piece(pieceTypeManager.fromString("N"), ChessSide.BLACK), c3)
            .addPiece(new Piece(pieceTypeManager.fromString("B"), ChessSide.WHITE), d4)
            .build();
    }
    
    @Test
    void testBuilderAddPiece_isOccupied_getPiece() {
        assertTrue(gameState.isOccupied(c3));
        assertEquals(new Piece(pieceTypeManager.fromString("N"), ChessSide.BLACK), gameState.getPiece(c3));
        assertTrue(gameState.isOccupied(d4));
        assertEquals(new Piece(pieceTypeManager.fromString("B"), ChessSide.WHITE), gameState.getPiece(d4));
        assertFalse(gameState.isOccupied(h8));
        assertEquals(null, gameState.getPiece(h8));
    }    

    @Test
    void testToBuilder_removePiece() {
        ChessGameStateImpl newGameState = gameState.toBuilder()
            .removePiece(c3)
            .addPiece(new Piece(pieceTypeManager.fromString("B"), ChessSide.BLACK), h8)
            .build();
        
        assertTrue(gameState.isOccupied(c3));
        assertEquals(new Piece(pieceTypeManager.fromString("N"), ChessSide.BLACK), gameState.getPiece(c3));
        assertTrue(gameState.isOccupied(d4));
        assertEquals(new Piece(pieceTypeManager.fromString("B"), ChessSide.WHITE), gameState.getPiece(d4));
        assertFalse(gameState.isOccupied(h8));
        assertEquals(null, gameState.getPiece(h8));

        
        assertFalse(newGameState.isOccupied(c3));
        assertEquals(null, newGameState.getPiece(c3));
        assertTrue(newGameState.isOccupied(d4));
        assertEquals(new Piece(pieceTypeManager.fromString("B"), ChessSide.WHITE), newGameState.getPiece(d4));
        assertTrue(newGameState.isOccupied(h8));
        assertEquals(new Piece(pieceTypeManager.fromString("B"), ChessSide.BLACK), newGameState.getPiece(h8));
    }
}
