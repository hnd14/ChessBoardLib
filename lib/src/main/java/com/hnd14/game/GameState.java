package com.hnd14.game;

public interface GameState {
    boolean hasPosition(Position position);

    boolean isOccupied(Position position);

    Piece getPiece(Position position);

}