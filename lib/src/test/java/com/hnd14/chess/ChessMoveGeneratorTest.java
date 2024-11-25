package com.hnd14.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hnd14.chess.piece.ChessSide;
import com.hnd14.chess.position.File;
import com.hnd14.chess.position.Rank;
import com.hnd14.game.GameState;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.piece.PieceTypeManager;

class ChessMoveGeneratorTest {
    private static PieceTypeManager pieceTypeManager = new PieceTypeManager();
    private static GameState gameState;
    private static final Position C3 = ChessPosition.builder().file(File.C).rank(Rank.THIRD).build();
    private static final Position C5 = ChessPosition.builder().file(File.C).rank(Rank.FIFTH).build();
    private static final Position D5 = ChessPosition.builder().file(File.D).rank(Rank.FIFTH).build();
    private static final Position D3 = ChessPosition.builder().file(File.D).rank(Rank.THIRD).build();
    private static final Position D4 = ChessPosition.builder().file(File.D).rank(Rank.FOURTH).build();
    private static final Position E4 = ChessPosition.builder().file(File.E).rank(Rank.FOURTH).build();
    @BeforeAll
    static void setup() {
        pieceTypeManager.register("Rook");
        pieceTypeManager.register("Queen");
        pieceTypeManager.register("Bishop");
        pieceTypeManager.register("Knight");
        pieceTypeManager.register("King");
        gameState = ChessGameStateImpl.builder()
            .addPiece(new Piece(pieceTypeManager.fromString("Rook"), ChessSide.BLACK), D3)
            .addPiece(new Piece(pieceTypeManager.fromString("Queen"), ChessSide.BLACK), C3)
            .addPiece(new Piece(pieceTypeManager.fromString("Bishop"), ChessSide.WHITE), D4)
            .addPiece(new Piece(pieceTypeManager.fromString("Queen"), ChessSide.WHITE), C5)
            .addPiece(new Piece(pieceTypeManager.fromString("King"), ChessSide.WHITE), D5)
            .addPiece(new Piece(pieceTypeManager.fromString("Knight"), ChessSide.BLACK), E4)
            .build();
    }

    @Test
    void testBishopMoveGenerator() {
        Piece whiteBishop = new Piece(pieceTypeManager.fromString("Bishop"), ChessSide.WHITE);
        List<Position> expectedEnd = List.of(
            new ChessPosition(Rank.FIFTH, File.E),
            new ChessPosition(Rank.SIXTH, File.F),
            new ChessPosition(Rank.SEVENTH, File.G),
            new ChessPosition(Rank.EIGHTH, File.H),
            C3,
            new ChessPosition(Rank.THIRD, File.E),
            new ChessPosition(Rank.SECOND, File.F),
            new ChessPosition(Rank.FIRST, File.G)
        ); 
        var result = ChessMoveGenerator.bishopMoveGenerator().generateMove(gameState, D4, whiteBishop);

        assertEquals(expectedEnd.size(), result.size());

        result.stream()
        .map(move -> {
            assertInstanceOf(ChessMove.class, move);
            return (ChessMove) move;
        })
        .forEach(move -> {
            assertEquals(D4, move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("Bishop"), ChessSide.WHITE), move.piece());
            assertTrue(expectedEnd.contains(move.end()));
        });
    }

    @Test
    void testQueenMoveGenerator() {
        Piece blackQueen = new Piece(pieceTypeManager.fromString("Queen"), ChessSide.BLACK);
        List<Position> expectedEnd = List.of(
            new ChessPosition(Rank.FIRST, File.A),
            new ChessPosition(Rank.FIRST, File.C),
            new ChessPosition(Rank.FIRST, File.E),
            new ChessPosition(Rank.SECOND, File.B),
            new ChessPosition(Rank.SECOND, File.C),
            new ChessPosition(Rank.SECOND, File.D),
            new ChessPosition(Rank.THIRD, File.B),
            new ChessPosition(Rank.THIRD, File.A),
            new ChessPosition(Rank.FOURTH, File.B),
            new ChessPosition(Rank.FOURTH, File.C),
            new ChessPosition(Rank.FOURTH, File.D),
            new ChessPosition(Rank.FIFTH, File.A),
            C5
        ); 
        var result = ChessMoveGenerator.queenMoveGenerator().generateMove(gameState, C3, blackQueen);

        assertEquals(expectedEnd.size(), result.size());

        result.stream()
        .map(move -> {
            assertInstanceOf(ChessMove.class, move);
            return (ChessMove) move;
        })
        .forEach(move -> {
            assertEquals(C3, move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("Queen"), ChessSide.BLACK), move.piece());
            assertTrue(expectedEnd.contains(move.end()));
        });
    }

    @Test
    void testRookMoveGenerator() {
        Piece blackRook = new Piece(pieceTypeManager.fromString("Rook"), ChessSide.BLACK);
        List<Position> expectedEnd = List.of(
            new ChessPosition(Rank.FIRST, File.D),
            new ChessPosition(Rank.SECOND, File.D),
            new ChessPosition(Rank.FOURTH, File.D),
            new ChessPosition(Rank.THIRD, File.E),
            new ChessPosition(Rank.THIRD, File.F),
            new ChessPosition(Rank.THIRD, File.G),
            new ChessPosition(Rank.THIRD, File.H)
        ); 
        var result = ChessMoveGenerator.rookMoveGenerator().generateMove(gameState, D3, blackRook);

        assertEquals(expectedEnd.size(), result.size());

        result.stream()
        .map(move -> {
            assertInstanceOf(ChessMove.class, move);
            return (ChessMove) move;
        })
        .forEach(move -> {
            assertEquals(D3, move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("Rook"), ChessSide.BLACK), move.piece());
            assertTrue(expectedEnd.contains(move.end()));
        });
    }

    @Test
    void testKingMoveGenerator() {
        Piece whiteKing = new Piece(pieceTypeManager.fromString("King"), ChessSide.WHITE);
        List<Position> expectedEnd = List.of(
            new ChessPosition(Rank.SIXTH, File.C),
            new ChessPosition(Rank.SIXTH, File.D),
            new ChessPosition(Rank.SIXTH, File.E),
            new ChessPosition(Rank.FIFTH, File.E),
            new ChessPosition(Rank.FOURTH, File.C),
            new ChessPosition(Rank.FOURTH, File.E)
        ); 
        var result = ChessMoveGenerator.kingMoveGenerator().generateMove(gameState, D5, whiteKing);

        assertEquals(expectedEnd.size(), result.size());

        result.stream()
        .map(move -> {
            assertInstanceOf(ChessMove.class, move);
            return (ChessMove) move;
        })
        .forEach(move -> {
            assertEquals(D5, move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("King"), ChessSide.WHITE), move.piece());
            assertTrue(expectedEnd.contains(move.end()));
        });
    }

    @Test
    void testKnightMoveGenerator() {
        Piece blackKnight = new Piece(pieceTypeManager.fromString("Knight"), ChessSide.BLACK);
        List<Position> expectedEnd = List.of(
            new ChessPosition(Rank.SIXTH, File.D),
            new ChessPosition(Rank.SIXTH, File.F),
            new ChessPosition(Rank.FIFTH, File.C),
            new ChessPosition(Rank.FIFTH, File.G),
            new ChessPosition(Rank.THIRD, File.G),
            new ChessPosition(Rank.SECOND, File.D),
            new ChessPosition(Rank.SECOND, File.F)
        ); 
        var result = ChessMoveGenerator.knightMoveGenerator().generateMove(gameState, E4, blackKnight);

        assertEquals(expectedEnd.size(), result.size());

        result.stream()
        .map(move -> {
            assertInstanceOf(ChessMove.class, move);
            return (ChessMove) move;
        })
        .forEach(move -> {
            assertEquals(E4, move.start());
            assertEquals(new Piece(pieceTypeManager.fromString("Knight"), ChessSide.BLACK), move.piece());
            assertTrue(expectedEnd.contains(move.end()));
        });
    }
}
