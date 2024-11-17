package com.hnd14.chess;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hnd14.game.GameState;

public class GameStateImpl implements GameState {
    private Map<Position, ChessPiece> state;
    private GameStateImpl(Map<Position, ChessPiece> state) {
        this.state = state;
    }

    @Override
    public boolean isOccupied (Position position) {
        return state.get(position) != null ;
    }

    @Override
    public ChessPiece getPiece(Position position) {
        return state.get(position);
    }

    public static GameStateBuilder builder() {
        return new GameStateBuilder();
    }

    public GameStateBuilder toBuilder() {
        return new GameStateBuilder(state);
    }

    public static class GameStateBuilder {
        private Map<Position, ChessPiece> state = new ConcurrentHashMap<>();

        private GameStateBuilder() {    
        }

        private GameStateBuilder(Map<Position, ChessPiece> state) {
            this.state = new ConcurrentHashMap<>(state);
        }

        public GameStateImpl build() {
            return new GameStateImpl(state);
        }

        public GameStateBuilder addPiece(ChessPiece piece, Position position) {
            state.put(position, piece);
            return this;
        } 

        public GameStateBuilder removePiece(Position position) {
            state.remove(position);
            return this;
        } 
    } 
}
