package com.hnd14.chess;

import com.hnd14.game.Move;

public record ChessMove(ChessPosition start, ChessPosition end) implements Move {

}
