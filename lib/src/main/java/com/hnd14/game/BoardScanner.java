package com.hnd14.game;

import java.util.List;

import com.hnd14.game.exception.NoPieceAtPositionException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.move.MoveGenerator;
import com.hnd14.game.move.MoveGeneratorRegistry;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardScanner {
    private GameState gameState;
    private MoveGeneratorRegistry moveGenerators;

    public List<Move> scan(Position position, Piece piece){
        if (!gameState.hasPosition(position)) {
            throw new PositionNotExistsException(position);
        }

        if (!piece.equals(gameState.getPiece(position))) {
            throw new NoPieceAtPositionException(position, piece);
        }

        MoveGenerator moveGenerator = moveGenerators.getMoveGenerator(piece.pieceType());
        return moveGenerator.generateMove(gameState, position, piece);
    }

    public List<Move> scan(Position position){
        Piece piece = getPerformingPiece(position);
        MoveGenerator moveGenerator = moveGenerators.getMoveGenerator(piece.pieceType());
        return moveGenerator.generateMove(gameState, position, piece);
    }

    private Piece getPerformingPiece(Position position) {
        if (!gameState.hasPosition(position)) {
            throw new PositionNotExistsException(position);
        }
        if (!gameState.isOccupied(position)) {
            throw new NoPieceAtPositionException(position);
        }
        return gameState.getPiece(position);
    }
}
