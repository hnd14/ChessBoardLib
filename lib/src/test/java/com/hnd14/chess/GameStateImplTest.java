package com.hnd14.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hnd14.chess.piece.PieceTypeManager;
import com.hnd14.chess.piece.Side;
import com.hnd14.chess.position.File;
import com.hnd14.chess.position.Rank;

class GameStateImplTest {
    static PieceTypeManager pieceTypeManager = new PieceTypeManager();
    Position c3 = new Position(Rank.THIRD, File.C);
    Position d4 = new Position(Rank.FOURTH, File.D);
    Position h8 = new Position(Rank.EIGHTH, File.H);
    GameStateImpl gameState;
    @BeforeAll
    static void setupBeforeAll () {
        pieceTypeManager.register("Bishop");
        pieceTypeManager.register("Knight");
        pieceTypeManager.registerAlias("B", "Bishop");
        pieceTypeManager.registerAlias("N", "Knight");
    } 

    @BeforeEach
    void setup() {
        gameState = GameStateImpl
            .builder()
            .addPiece(new ChessPiece(pieceTypeManager.fromString("N"), Side.BLACK), c3)
            .addPiece(new ChessPiece(pieceTypeManager.fromString("B"), Side.WHITE), d4)
            .build();
    }
    
    @Test
    void testBuilderAddPiece_isOccupied_getPiece() {
        assertTrue(gameState.isOccupied(c3));
        assertEquals(new ChessPiece(pieceTypeManager.fromString("N"), Side.BLACK), gameState.getPiece(c3));
        assertTrue(gameState.isOccupied(d4));
        assertEquals(new ChessPiece(pieceTypeManager.fromString("B"), Side.WHITE), gameState.getPiece(d4));
        assertFalse(gameState.isOccupied(h8));
        assertEquals(null, gameState.getPiece(h8));
    }    

    @Test
    void testToBuilder_removePiece() {
        GameStateImpl newGameState = gameState.toBuilder()
            .removePiece(c3)
            .addPiece(new ChessPiece(pieceTypeManager.fromString("B"), Side.BLACK), h8)
            .build();
        
        assertTrue(gameState.isOccupied(c3));
        assertEquals(new ChessPiece(pieceTypeManager.fromString("N"), Side.BLACK), gameState.getPiece(c3));
        assertTrue(gameState.isOccupied(d4));
        assertEquals(new ChessPiece(pieceTypeManager.fromString("B"), Side.WHITE), gameState.getPiece(d4));
        assertFalse(gameState.isOccupied(h8));
        assertEquals(null, gameState.getPiece(h8));

        
        assertFalse(newGameState.isOccupied(c3));
        assertEquals(null, newGameState.getPiece(c3));
        assertTrue(newGameState.isOccupied(d4));
        assertEquals(new ChessPiece(pieceTypeManager.fromString("B"), Side.WHITE), newGameState.getPiece(d4));
        assertTrue(newGameState.isOccupied(h8));
        assertEquals(new ChessPiece(pieceTypeManager.fromString("B"), Side.BLACK), newGameState.getPiece(h8));
    }
}
