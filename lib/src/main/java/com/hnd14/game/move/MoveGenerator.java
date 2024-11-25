package com.hnd14.game.move;

import java.util.List;

import com.hnd14.game.GameState;
import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.PositionNotExistsException;

public interface MoveGenerator {
    /**
     * Calculate the moves a performing piece starting at the start position can make given the game state
     * @param gameState the current game state
     * @param start the starting position
     * @param performingPiece the piece 
     * @throws PositionNotExistsException when the starting position is not in the gameState
     * @return the list of possible moves can be make if by the piece if they are standing at the start position 
     */
    List<Move> generateMove(GameState gameState, Position start, Piece performingPiece);
}
