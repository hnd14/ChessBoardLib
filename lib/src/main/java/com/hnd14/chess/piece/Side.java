package com.hnd14.chess.piece;

import lombok.Getter;

public enum Side {
    BLACK(-1),
    WHITE(1);
    @Getter
    private int forward;
    private Side(int forward) {
        this.forward = forward;
    }
}
