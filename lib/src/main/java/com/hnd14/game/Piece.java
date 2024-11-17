package com.hnd14.game;

import com.hnd14.game.piece.Side;
import com.hnd14.game.piece.PieceTypeManager.PieceType;

public record Piece(PieceType pieceType, Side side) {

}
