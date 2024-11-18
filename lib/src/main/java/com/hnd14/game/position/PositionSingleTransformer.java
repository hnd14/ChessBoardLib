package com.hnd14.game.position;

import com.hnd14.game.Position;

@FunctionalInterface
public interface PositionSingleTransformer {
    /**
     * Allow transforming a Position to another Position
     * @param position The starting Position
     * @return The transformed Position
     * @throws com.hnd14.game.exception.PositionCannotBeTransformedException 
     * when the input position cannot be transformed
     */
    Position transform(Position position);
}
