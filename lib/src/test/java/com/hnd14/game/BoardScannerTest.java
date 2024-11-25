package com.hnd14.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hnd14.chess.ChessGameStateImpl;
import com.hnd14.chess.ChessMoveGenerator;
import com.hnd14.chess.ChessPosition;
import com.hnd14.chess.piece.ChessSide;
import com.hnd14.chess.position.File;
import com.hnd14.chess.position.Rank;
import com.hnd14.game.exception.NoPieceAtPositionException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.move.MoveGeneratorRegistry;
import com.hnd14.game.piece.PieceTypeManager;

class BoardScannerTest {
    private static PieceTypeManager chessPieceTypeManager = new PieceTypeManager();
    private static MoveGeneratorRegistry chessGeneratorRegistry = new MoveGeneratorRegistry(chessPieceTypeManager);
    
    private static final Position C3 = ChessPosition.builder().file(File.C).rank(Rank.THIRD).build();
    private static final Position C5 = ChessPosition.builder().file(File.C).rank(Rank.FIFTH).build();
    private static final Position D5 = ChessPosition.builder().file(File.D).rank(Rank.FIFTH).build();
    private static final Position D3 = ChessPosition.builder().file(File.D).rank(Rank.THIRD).build();
    private static final Position D2 = ChessPosition.builder().file(File.D).rank(Rank.SECOND).build();
    private static final Position B2 = ChessPosition.builder().file(File.B).rank(Rank.SECOND).build();
    private static final Position D4 = ChessPosition.builder().file(File.D).rank(Rank.FOURTH).build();
    private static final Position E4 = ChessPosition.builder().file(File.E).rank(Rank.FOURTH).build();
    private static final Position E6 = ChessPosition.builder().file(File.E).rank(Rank.SIXTH).build();
    private static final Position F7 = ChessPosition.builder().file(File.F).rank(Rank.SEVENTH).build();

    @BeforeAll 
    static void setup() {
        chessPieceTypeManager.register("Rook");
        chessGeneratorRegistry.register("Rook", ChessMoveGenerator.rookMoveGenerator());

        chessPieceTypeManager.register("Queen");
        chessGeneratorRegistry.register("Queen", ChessMoveGenerator.queenMoveGenerator());
        
        chessPieceTypeManager.register("Bishop");
        chessGeneratorRegistry.register("Bishop", ChessMoveGenerator.bishopMoveGenerator());
        
        chessPieceTypeManager.register("Knight");
        chessGeneratorRegistry.register("Knight", ChessMoveGenerator.knightMoveGenerator());
        
        chessPieceTypeManager.register("King");
        chessGeneratorRegistry.register("King", ChessMoveGenerator.kingMoveGenerator());
        
        chessPieceTypeManager.register("Black Pawn");
        chessGeneratorRegistry.register("Black Pawn", ChessMoveGenerator.blackPawnMoveGenerator());
        
        chessPieceTypeManager.register("White Pawn");
        chessGeneratorRegistry.register("White Pawn", ChessMoveGenerator.whitePawnMoveGenerator());
        

    }
    @Test
    void testScanPositionOnly() {
        Piece whitePawn = new Piece(chessPieceTypeManager.fromString("White Pawn"), ChessSide.WHITE);
        GameState gameState = ChessGameStateImpl.builder()
            .addPiece(new Piece(chessPieceTypeManager.fromString("Rook"), ChessSide.BLACK), D3)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Queen"), ChessSide.BLACK), C3)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Bishop"), ChessSide.WHITE), D4)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Queen"), ChessSide.WHITE), C5)
            .addPiece(new Piece(chessPieceTypeManager.fromString("King"), ChessSide.WHITE), D5)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Knight"), ChessSide.BLACK), E4)
            .addPiece(new Piece(chessPieceTypeManager.fromString("White Pawn"), ChessSide.WHITE), B2)
            .addPiece(new Piece(chessPieceTypeManager.fromString("White Pawn"), ChessSide.WHITE), D2)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Black Pawn"), ChessSide.BLACK), E6)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Black Pawn"), ChessSide.BLACK), F7)
            .build();

        BoardScanner scanner = new BoardScanner(gameState, chessGeneratorRegistry);
        Position outOfBoard = new Position() {};
        Position a8 = new ChessPosition(Rank.EIGHTH, File.A);

        assertThrows(PositionNotExistsException.class, () -> scanner.scan(outOfBoard));
        assertThrows(NoPieceAtPositionException.class, () -> scanner.scan(a8));

        assertEquals(ChessMoveGenerator.whitePawnMoveGenerator()
            .generateMove(gameState, B2, whitePawn), 
            scanner.scan(B2));
    }

    @Test
    void testScanPositionAndPiece() {
        GameState gameState = ChessGameStateImpl.builder()
            .addPiece(new Piece(chessPieceTypeManager.fromString("Rook"), ChessSide.BLACK), D3)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Queen"), ChessSide.BLACK), C3)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Bishop"), ChessSide.WHITE), D4)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Queen"), ChessSide.WHITE), C5)
            .addPiece(new Piece(chessPieceTypeManager.fromString("King"), ChessSide.WHITE), D5)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Knight"), ChessSide.BLACK), E4)
            .addPiece(new Piece(chessPieceTypeManager.fromString("White Pawn"), ChessSide.WHITE), B2)
            .addPiece(new Piece(chessPieceTypeManager.fromString("White Pawn"), ChessSide.WHITE), D2)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Black Pawn"), ChessSide.BLACK), E6)
            .addPiece(new Piece(chessPieceTypeManager.fromString("Black Pawn"), ChessSide.BLACK), F7)
            .build();

        BoardScanner scanner = new BoardScanner(gameState, chessGeneratorRegistry);
        Piece whitePawn = new Piece(chessPieceTypeManager.fromString("White Pawn"), ChessSide.WHITE);
        Piece whiteKing = new Piece(chessPieceTypeManager.fromString("King"), ChessSide.WHITE);
        Position outOfBoard = new Position() {};

        assertThrows(PositionNotExistsException.class, () -> scanner.scan(outOfBoard, whiteKing));
        assertThrows(NoPieceAtPositionException.class, () -> scanner.scan(B2, whiteKing));

        assertEquals(ChessMoveGenerator.whitePawnMoveGenerator()
            .generateMove(gameState, B2, whitePawn), 
            scanner.scan(B2, whitePawn));
    }
}
