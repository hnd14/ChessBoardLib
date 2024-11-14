package com.hnd14.chess.position;

import lombok.Builder;

@Builder(toBuilder = true)
public record Position(Rank rank, File file) {

}
