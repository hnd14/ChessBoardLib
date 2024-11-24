package com.hnd14.game.move;

import java.util.LinkedList;
import java.util.List;

import com.hnd14.game.GameState;
import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.NoPieceAtPositionException;
import com.hnd14.game.exception.PositionCannotBeTransformedException;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.position.PositionSingleTransformer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinearUntilBlockedMoveGenerator implements MoveGenerator {
    private PositionSingleTransformer direction;
    private MoveFactory moveFactory;

    @Override   
    public List<Move> generateMove(GameState gameState, Position start) {
        if (!gameState.hasPosition(start)) {
            throw new PositionNotExistsException(start);
        }
        if (!gameState.isOccupied(start)) {
            throw new NoPieceAtPositionException(start);
        }
        Piece performingPiece = gameState.getPiece(start);
        List<Move> result = new LinkedList<>();
        Position current = nextPosition(gameState, start);   
        while (current != null) {
            if (!gameState.isOccupied(current) || !gameState.getPiece(current).side().isAllyWith(performingPiece.side())){
                result.add(moveFactory.createMove(start, current, performingPiece));
            }
            if (gameState.isOccupied(current)) {
                current = null;
            }
            current = nextPosition(gameState, current);
        }    
        return result;
    }

    private Position nextPosition(GameState gameState, Position position) {
        try {
            Position nextPosition = direction.transform(position); 
            if (gameState.hasPosition(nextPosition)){
                return nextPosition;
            }
            return null;
        } 
        catch(PositionCannotBeTransformedException e) {
            return null;
        }
    }
    
}
