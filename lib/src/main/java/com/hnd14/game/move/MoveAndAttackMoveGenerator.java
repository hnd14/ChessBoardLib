package com.hnd14.game.move;

import java.util.List;

import com.hnd14.game.GameState;
import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.exception.PositionNotExistsException;
import com.hnd14.game.position.PositionSingleTransformer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class MoveAndAttackMoveGenerator implements MoveGenerator {
    private PositionSingleTransformer transformer;
    private MoveFactory moveFactory;

    @Override
    public List<Move> generateMove(GameState gameState, Position start, Piece performingPiece) {
        if (!gameState.hasPosition(start)) {
            throw new PositionNotExistsException(start);
        }
        Position end = MoveGeneratorUtil.applyTransformer(transformer, gameState, start);
        if (end == null || !requirements(gameState, start, end, performingPiece)) {
            return List.of();
        }
        return List.of(moveFactory.createMove(start, end, performingPiece));
    }

    protected abstract boolean requirements(GameState gameState, Position start, Position target, Piece performingPiece);
}
