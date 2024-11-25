package com.hnd14.chess;

import com.hnd14.chess.piece.ChessSide;
import com.hnd14.chess.position.Rank;
import com.hnd14.chess.position.SimpleChessPositionTransformer;
import com.hnd14.game.GameState;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.move.AggregateMoveGenerator;
import com.hnd14.game.move.LinearUntilBlockedMoveGenerator;
import com.hnd14.game.move.MoveAndAttackMoveGenerator;
import com.hnd14.game.move.MoveGenerator;
import com.hnd14.game.position.PositionSingleTransformer;

public class ChessMoveGenerator {
    private ChessMoveGenerator() {
        
    }
    private static PositionSingleTransformer chessPositionTransformer(int fileValue, int rankValue) {
        return SimpleChessPositionTransformer.builder()
            .fileValue(fileValue)
            .rankValue(rankValue)
            .build();
    }

    private static MoveGenerator chessLinearMoveGenerator(int fileValue, int rankValue) {
        return new LinearUntilBlockedMoveGenerator(chessPositionTransformer(fileValue, rankValue), 
                                                    ChessMove::createMove);
    }

    private static MoveGenerator moveOrAttackGenerator(int fileValue, int rankValue) {
        return new MoveAndAttackMoveGenerator(chessPositionTransformer(fileValue, rankValue), ChessMove::createMove) {
            @Override
            protected boolean requirements(GameState gameState, Position start, Position target, Piece performingPiece) {
                return !gameState.isOccupied(target) || !gameState.getPiece(target).side().isAllyWith(performingPiece.side());
            }
        };
    }

    private static MoveGenerator moveToEmptySpaceGenerator(int fileValue, int rankValue) {
        return new MoveAndAttackMoveGenerator(chessPositionTransformer(fileValue, rankValue), ChessMove::createMove) {
            @Override
            protected boolean requirements(GameState gameState, Position start, Position target, Piece performingPiece) {
                return !gameState.isOccupied(target);
            }
        };
    }

    private static MoveGenerator attackMoveGenerator(int fileValue, int rankValue) {
        return new MoveAndAttackMoveGenerator(chessPositionTransformer(fileValue, rankValue), ChessMove::createMove) {
            @Override
            protected boolean requirements(GameState gameState, Position start, Position target, Piece performingPiece) {
                return gameState.isOccupied(target) && !gameState.getPiece(target).side().isAllyWith(performingPiece.side());
            }
        };
    }

    public static MoveGenerator rookMoveGenerator() {
        return AggregateMoveGenerator.builder()
            .addGenerator(chessLinearMoveGenerator(0, 1))
            .addGenerator(chessLinearMoveGenerator(0, -1))
            .addGenerator(chessLinearMoveGenerator(-1, 0))
            .addGenerator(chessLinearMoveGenerator(1, 0))
            .build();
    }

    public static MoveGenerator bishopMoveGenerator() {
        return AggregateMoveGenerator.builder()
            .addGenerator(chessLinearMoveGenerator(1, 1))
            .addGenerator(chessLinearMoveGenerator(-1, -1))
            .addGenerator(chessLinearMoveGenerator(-1, 1))
            .addGenerator(chessLinearMoveGenerator(1, -1))
            .build();
    }

    public static MoveGenerator queenMoveGenerator() {
        return AggregateMoveGenerator.builder()
            .addGenerator(chessLinearMoveGenerator(0, 1))
            .addGenerator(chessLinearMoveGenerator(0, -1))
            .addGenerator(chessLinearMoveGenerator(-1, 0))
            .addGenerator(chessLinearMoveGenerator(1, 0))
            .addGenerator(chessLinearMoveGenerator(1, 1))
            .addGenerator(chessLinearMoveGenerator(-1, -1))
            .addGenerator(chessLinearMoveGenerator(-1, 1))
            .addGenerator(chessLinearMoveGenerator(1, -1))
            .build();
    }

    public static MoveGenerator knightMoveGenerator() {
        return AggregateMoveGenerator.builder()
            .addGenerator(moveOrAttackGenerator(1, 2))
            .addGenerator(moveOrAttackGenerator(1, -2))
            .addGenerator(moveOrAttackGenerator(-1, 2))
            .addGenerator(moveOrAttackGenerator(-1, -2))
            .addGenerator(moveOrAttackGenerator(2, 1))
            .addGenerator(moveOrAttackGenerator(-2, 1))
            .addGenerator(moveOrAttackGenerator(2, -1))
            .addGenerator(moveOrAttackGenerator(-2, -1))
            .build();
    }

    public static MoveGenerator kingMoveGenerator() {
        return AggregateMoveGenerator.builder()
            .addGenerator(moveOrAttackGenerator(1, 1))
            .addGenerator(moveOrAttackGenerator(1, -1))
            .addGenerator(moveOrAttackGenerator(-1, 1))
            .addGenerator(moveOrAttackGenerator(-1, -1))
            .addGenerator(moveOrAttackGenerator(0, 1))
            .addGenerator(moveOrAttackGenerator(-1, 0))
            .addGenerator(moveOrAttackGenerator(1, 0))
            .addGenerator(moveOrAttackGenerator(0, -1))
            .build();
    }

    private static MoveGenerator blackPawnTwoStepMoveGenerator() {
        return new MoveAndAttackMoveGenerator(chessPositionTransformer(0, -2), ChessMove::createMove) {
            @Override
            protected boolean requirements(GameState gameState, Position start, Position target, Piece performingPiece) {
                if (!(start instanceof ChessPosition castedPosition)){
                    return false;
                }
                // The current position is not on rank seven
                if (!castedPosition.rank().equals(Rank.SEVENTH)){
                    return false;
                }
                // The position infront of it is occupied
                if (gameState.isOccupied(castedPosition.toBuilder().rank(Rank.SIXTH).build())){
                    return false;
                }
                return !gameState.isOccupied(target) && performingPiece.side().equals(ChessSide.BLACK);
            }
        };
    }

    public static MoveGenerator blackPawnMoveGenerator() {
        return AggregateMoveGenerator.builder()
            .addGenerator(moveToEmptySpaceGenerator(0, -1))
            .addGenerator(attackMoveGenerator(1, -1))
            .addGenerator(attackMoveGenerator(-1, -1))
            .addGenerator(blackPawnTwoStepMoveGenerator())
            .build();
    }

    private static MoveGenerator whitePawnTwoStepMoveGenerator() {
        return new MoveAndAttackMoveGenerator(chessPositionTransformer(0, 2), ChessMove::createMove) {
            @Override
            protected boolean requirements(GameState gameState, Position start, Position target, Piece performingPiece) {
                if (!(start instanceof ChessPosition castedPosition)){
                    return false;
                }
                // The current position is not on rank seven
                if (!castedPosition.rank().equals(Rank.SECOND)){
                    return false;
                }
                // The position infront of it is occupied
                if (gameState.isOccupied(castedPosition.toBuilder().rank(Rank.THIRD).build())){
                    return false;
                }
                return !gameState.isOccupied(target) && performingPiece.side().equals(ChessSide.WHITE);
            }
        };
    }

    
    public static MoveGenerator whitePawnMoveGenerator() {
        return AggregateMoveGenerator.builder()
            .addGenerator(moveToEmptySpaceGenerator(0, 1))
            .addGenerator(attackMoveGenerator(1, 1))
            .addGenerator(attackMoveGenerator(-1, 1))
            .addGenerator(whitePawnTwoStepMoveGenerator())
            .build();
    }
}
