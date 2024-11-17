package com.hnd14.chess.exception;

public class PieceTypeDoesNotExistException extends RuntimeException {
    public PieceTypeDoesNotExistException(String type) {
        super(String.format("Piece type %s has not been registered", type));
    }
}
