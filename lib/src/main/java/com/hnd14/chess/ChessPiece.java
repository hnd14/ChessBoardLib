package com.hnd14.chess;

import com.hnd14.chess.piece.Side;
import com.hnd14.chess.piece.PieceTypeManager.PieceType;

public record ChessPiece(PieceType pieceType, Side side) {

}
