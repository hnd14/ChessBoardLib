package com.hnd14.chess;

import com.hnd14.chess.position.File;
import com.hnd14.chess.position.Rank;

import lombok.Builder;

@Builder(toBuilder = true)
public record Position(Rank rank, File file) {

}
