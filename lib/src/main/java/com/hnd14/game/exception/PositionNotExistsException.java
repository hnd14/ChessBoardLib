package com.hnd14.game.exception;

import com.hnd14.game.Position;

public class PositionNotExistsException extends RuntimeException {
    public PositionNotExistsException(Position position) {
        super(String.format("Position %s does not exist in the current board.", position.toString()));
    }
}
