package com.hnd14.game.move;

import java.util.List;

import com.hnd14.game.GameState;
import com.hnd14.game.Move;
import com.hnd14.game.Position;

public interface MoveGenerator {
    List<Move> generateMove(GameState gameState, Position start);
}
