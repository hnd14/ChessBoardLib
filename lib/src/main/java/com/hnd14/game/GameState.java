package com.hnd14.game;

import com.hnd14.chess.ChessPiece;

public interface GameState {
    boolean hasPosition(Position position);

    boolean isOccupied(Position position);

    ChessPiece getPiece(Position position);

}