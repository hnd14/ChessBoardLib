package com.hnd14.chess.position;

import com.hnd14.chess.exception.RankNotExistException;

public enum Rank {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8);
    
    private int value;
    Rank(int i) {
        value = i;
    }

    public static Rank fromNumber(int i) {
        for (Rank b : Rank.values()) {
            if (b.value == i) {
                return b;
            }
        }

        throw(new RankNotExistException(i));
    }
    
    
    public static Rank inc(Rank rank, int i) {
        return Rank.fromNumber(rank.value + i);
    }

    public static Rank dec(Rank rank, int i) {
        return Rank.fromNumber(rank.value - i);
    }
}
