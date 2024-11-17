package com.hnd14.chess.piece;

import com.hnd14.game.piece.Side;

import lombok.Getter;

public enum ChessSide implements Side {
    BLACK(-1),
    WHITE(1);
    @Getter
    private int forward;
    private ChessSide(int forward) {
        this.forward = forward;
    }
    @Override
    public boolean isAllyWith(Side side) {
        return this.equals(side);
    }
}
