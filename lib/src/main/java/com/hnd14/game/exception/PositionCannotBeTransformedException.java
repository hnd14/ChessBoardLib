package com.hnd14.game.exception;

public class PositionCannotBeTransformedException extends RuntimeException {
    public PositionCannotBeTransformedException(String message) {
        super(message);
    }

    public PositionCannotBeTransformedException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
