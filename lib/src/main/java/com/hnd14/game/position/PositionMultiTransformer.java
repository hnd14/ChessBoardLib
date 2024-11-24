package com.hnd14.game.position;

import java.util.List;

import com.hnd14.game.Position;

@FunctionalInterface
public interface PositionMultiTransformer {
    List<Position> transformPositions(Position position); 
}
