package com.hnd14.chess;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hnd14.game.GameState;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;

public class ChessGameStateImpl implements GameState {
    private Map<Position, Piece> state;
    private ChessGameStateImpl(Map<Position, Piece> state) {
        this.state = state;
    }

    @Override
    public boolean isOccupied (Position position) {
        return state.get(position) != null ;
    }

    @Override
    public Piece getPiece(Position position) {
        return state.get(position);
    }

    @Override
    public boolean hasPosition(Position position) {
        return position instanceof ChessPosition;
    } 

    public static GameStateBuilder builder() {
        return new GameStateBuilder();
    }

    public GameStateBuilder toBuilder() {
        return new GameStateBuilder(state);
    }
    
    public static class GameStateBuilder {
        private Map<Position, Piece> state = new ConcurrentHashMap<>();

        private GameStateBuilder() {    
        }

        private GameStateBuilder(Map<Position, Piece> state) {
            this.state = new ConcurrentHashMap<>(state);
        }

        public ChessGameStateImpl build() {
            return new ChessGameStateImpl(state);
        }

        public GameStateBuilder addPiece(Piece piece, Position position) {
            state.put(position, piece);
            return this;
        } 

        public GameStateBuilder removePiece(Position position) {
            state.remove(position);
            return this;
        } 
    }

}
