package com.hnd14.chess.position;

import com.hnd14.chess.exception.FileNotExistException;

public enum File {
    A(1, 'A'),
    B(2, 'B'),
    C(3, 'C'),
    D(4, 'D'),
    E(5, 'E'),
    F(6, 'F'),
    G(7, 'G'),
    H(8, 'H');
    private int value;
    private char name;
    File (int i, char c) {
        value = i;
        name = c;
    }

    public static File fromChar (char c) {
        char cap = Character.toUpperCase(c);
        for (File b : File.values()) {
            if (b.name == cap) {
                return b;
            }
        }

        throw(new FileNotExistException(cap));
    }

    public static File fromValue (int i) {
        for (File b : File.values()) {
            if (b.value == i) {
                return b;
            }
        }

        throw(new FileNotExistException());
    }

    public static File inc(File file, int i) {
        return File.fromValue(file.value + i);
    }

    
    public static File dec(File file, int i) {
        return File.fromValue(file.value - i);
    }
}
