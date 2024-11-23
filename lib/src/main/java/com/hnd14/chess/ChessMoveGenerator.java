package com.hnd14.chess;

import com.hnd14.chess.position.SimpleChessPositionTransformer;
import com.hnd14.game.move.AggregateMoveGenerator;
import com.hnd14.game.move.LinearUntilBlockedMoveGenerator;
import com.hnd14.game.move.MoveGenerator;
import com.hnd14.game.position.PositionSingleTransformer;

public class ChessMoveGenerator {
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
}
