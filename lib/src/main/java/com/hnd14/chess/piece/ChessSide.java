package com.hnd14.chess.piece;

import com.hnd14.game.piece.Side;

public enum ChessSide implements Side {
    BLACK,
    WHITE;
    @Override
    public boolean isAllyWith(Side side) {
        return this.equals(side);
    }
}
