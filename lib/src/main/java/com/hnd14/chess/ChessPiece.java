package com.hnd14.chess;

import com.hnd14.chess.piece.PieceTypeManager.PieceType;
import com.hnd14.game.Side;

public record ChessPiece(PieceType pieceType, Side side) {

}
