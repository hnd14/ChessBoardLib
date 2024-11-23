package com.hnd14.game.move;

import java.util.LinkedList;
import java.util.List;

import com.hnd14.game.GameState;
import com.hnd14.game.Move;
import com.hnd14.game.Position;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AggregateMoveGenerator implements MoveGenerator {
    private List<MoveGenerator> generators;

    @Override
    public List<Move> generateMove(GameState gameState, Position start) {
        List<Move> result = new LinkedList<>();
        generators.stream()
            .forEach(generator -> result.addAll(generator.generateMove(gameState, start)));
        return result;
    }

    public static AggregateMoveGeneratorBuilder builder() {
        return new AggregateMoveGeneratorBuilder();
    }
    
    public static class AggregateMoveGeneratorBuilder {
        private List<MoveGenerator> generators;

        private AggregateMoveGeneratorBuilder() {
            generators = new LinkedList<>();
        }

        public AggregateMoveGeneratorBuilder addGenerator(MoveGenerator moveGenerator) {
            generators.add(moveGenerator);
            return this;
        }

        public AggregateMoveGenerator build() {
            return new AggregateMoveGenerator(generators);
        }
    }
}
