package com.hnd14.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hnd14.chess.exception.RankNotExistException;

public class RankTest {
    @Test
    void testFromNumber_validNumber_shouldReturnCorrectRank() {
        Rank result = Rank.fromNumber(8);
        assertEquals(Rank.EIGHTH, result);
    }

    @Test
    void testFromNumber_invalidNumber_shouldThrowRankNotExistException() {
        assertThrows(RankNotExistException.class, () -> Rank.fromNumber(0));
        assertThrows(RankNotExistException.class, () -> Rank.fromNumber(9));    
    }

    @Test
    void testInc_validInc_shouldReturnCorrectRank() {
        Rank start = Rank.SIXTH;
        Rank result = Rank.inc(start, 2);
        assertEquals(Rank.EIGHTH, result);
    }

    @Test
    void testInc_invalidInc_shouldThrowRankNotExistException() {
        Rank start = Rank.SIXTH;
        assertThrows(RankNotExistException.class, () -> Rank.inc(start, 3));
    }

    
    @Test
    void testDev_valid_shouldReturnCorrectRank() {
        Rank start = Rank.SIXTH;
        Rank result = Rank.dec(start, 2);
        assertEquals(Rank.FOURTH, result);
    }

    @Test
    void testDev_invalid_shouldThrowRankNotExistException() {
        Rank start = Rank.SECOND;
        assertThrows(RankNotExistException.class, () -> Rank.dec(start, 3));
    }
}
