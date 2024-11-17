package com.hnd14.chess;

import com.hnd14.chess.position.File;
import com.hnd14.chess.position.Rank;
import com.hnd14.game.Position;

import lombok.Builder;

@Builder(toBuilder = true)
public record ChessPosition(Rank rank, File file) implements Position {

}
