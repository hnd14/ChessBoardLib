package com.hnd14.chess.position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.hnd14.chess.exception.FileNotExistException;

public class FileTest {
    @Test
    void testFromChar() {
        // valid cases
        assertEquals(File.A, File.fromChar('a'));
        assertEquals(File.A, File.fromChar('A'));

        // invalid cases
        assertThrows(FileNotExistException.class, () -> File.fromChar('i'));
        assertThrows(FileNotExistException.class, () -> File.fromChar('-'));
    }

    @Test
    void testFromValue() {
        // valid cases
        assertEquals(File.A, File.fromValue(1));

        // invalid cases
        assertThrows(FileNotExistException.class, () -> File.fromValue(0));
        assertThrows(FileNotExistException.class, () -> File.fromValue(9));    
    }

    @Test
    void testInc() {
        // valid cases
        assertEquals(File.C, File.inc(File.A, 2));
        assertEquals(File.E, File.inc(File.B, 3));

        // invalid cases

        
        assertThrows(FileNotExistException.class, () -> File.inc(File.H,1));
        assertThrows(FileNotExistException.class, () -> File.inc(File.F,3));
    }

    @Test
    void testDec() {
        
        // valid cases
        assertEquals(File.A, File.dec(File.C, 2));
        assertEquals(File.B, File.dec(File.E, 3));

        // invalid cases
        assertThrows(FileNotExistException.class, () -> File.dec(File.A,1));
        assertThrows(FileNotExistException.class, () -> File.dec(File.B,3));
    }
}
