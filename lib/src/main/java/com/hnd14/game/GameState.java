package com.hnd14.game;

import com.hnd14.chess.ChessPiece;
import com.hnd14.chess.Position;

public interface GameState {

    boolean isOccupied(Position position);

    ChessPiece getPiece(Position position);

}