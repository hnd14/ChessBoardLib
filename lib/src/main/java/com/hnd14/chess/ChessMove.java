package com.hnd14.chess;

import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.InvalidPositionTypeException;

import lombok.Builder;


@Builder(toBuilder = true)
public record ChessMove(Piece piece, ChessPosition start, ChessPosition end) implements Move {
    public static Move createMove(Position start, Position end, Piece piece) {
        if (!(start instanceof ChessPosition) || !(end instanceof ChessPosition)){
            throw new InvalidPositionTypeException("Cannot create chess move from other game positions!");
        }
        if (start.equals(end)){
            throw new InvalidPositionTypeException("Cannot create staying in place move!");
        }
        return new ChessMove(piece, (ChessPosition) start, (ChessPosition) end);
    }
}
