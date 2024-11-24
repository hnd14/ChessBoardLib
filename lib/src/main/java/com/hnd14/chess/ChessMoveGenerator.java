package com.hnd14.chess;

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
                return !gameState.isOccupied(target) || !gameState.getPiece(target).side()
                    .isAllyWith(performingPiece.side());
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
}
