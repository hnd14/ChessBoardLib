package com.hnd14.chess.exception;

public class RankNotExistException extends RuntimeException {
    public RankNotExistException(int i){
        super(String.format("Rank %d does not exists in the board!", i));
    }
}
