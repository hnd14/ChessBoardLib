package com.hnd14.game.move;

import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;

@FunctionalInterface
public interface MoveFactory {
    public Move createMove(Position start, Position end, Piece piece);
}
