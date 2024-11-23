package com.hnd14.chess.position;

import com.hnd14.chess.ChessPosition;
import com.hnd14.chess.exception.FileNotExistException;
import com.hnd14.chess.exception.RankNotExistException;
import com.hnd14.game.Position;
import com.hnd14.game.exception.PositionCannotBeTransformedException;
import com.hnd14.game.position.PositionSingleTransformer;

import lombok.Builder;

@Builder(toBuilder = true)
public class SimpleChessPositionTransformer implements PositionSingleTransformer {
    private int fileValue;
    private int rankValue;
    @Override
    public Position transform(Position position) {
        if (!(position instanceof ChessPosition)) {
            throw new PositionCannotBeTransformedException(String.format("%s is not an instance of chess position", position));
        }
        ChessPosition chessPosition = (ChessPosition) position;
        try {
            return ChessPosition.builder()
                        .file(File.inc(chessPosition.file(), fileValue))
                        .rank(Rank.inc(chessPosition.rank(), rankValue))
                        .build();
        } catch (FileNotExistException | RankNotExistException e) {
            throw new PositionCannotBeTransformedException("Transform out of edge.", e);    
        }
    }

}
