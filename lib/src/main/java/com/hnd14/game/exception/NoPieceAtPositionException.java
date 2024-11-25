package com.hnd14.game.exception;

import com.hnd14.game.Piece;
import com.hnd14.game.Position;

public class NoPieceAtPositionException extends RuntimeException {
    public NoPieceAtPositionException(Position position) {
        super(String.format("No piece at %s.", position.toString()));
    }

    public NoPieceAtPositionException(Position position, Piece piece) {
        super(String.format("No piece %s at %s.", piece.toString(), position.toString()));
    }
}
