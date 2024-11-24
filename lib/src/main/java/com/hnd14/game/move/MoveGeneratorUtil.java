package com.hnd14.game.move;

import com.hnd14.game.GameState;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.NoPieceAtPositionException;
import com.hnd14.game.exception.PositionCannotBeTransformedException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.position.PositionSingleTransformer;

public class MoveGeneratorUtil {
    private MoveGeneratorUtil(){};
    static Position applyTransformer (PositionSingleTransformer transformer, GameState gameState, Position position) {
        if (!(gameState.hasPosition(position))) {
            return null;
        }
        
        try {
            Position nextPosition = transformer.transform(position); 
            if (gameState.hasPosition(nextPosition)){
                return nextPosition;
            }
            return null;
        } 
        catch(PositionCannotBeTransformedException e) {
            return null;
        }
    }

    static Piece getPerformingPiece(GameState gameState, Position position) {
        if (!gameState.hasPosition(position)) {
            throw new PositionNotExistsException(position);
        }
        if (!gameState.isOccupied(position)) {
            throw new NoPieceAtPositionException(position);
        }
        return gameState.getPiece(position);
    }
}
