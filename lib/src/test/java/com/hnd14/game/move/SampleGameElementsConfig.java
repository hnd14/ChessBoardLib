package com.hnd14.game.move;

import java.util.Map;

import com.hnd14.game.GameState;
import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.piece.Side;

public class SampleGameElementsConfig {
    record SamplePosition(int x, int y) implements Position{
    }



    record SampleMove(Position start, Position end, Piece piece) implements Move {
    }

    class SampleGameState implements GameState {
        private Map<SamplePosition, Piece> state;

        public SampleGameState(Map<SamplePosition, Piece> state) {
            this.state = state;
        }

        @Override
        public boolean hasPosition(Position position) {
            return (position instanceof SamplePosition samplePosition) 
                && samplePosition.x() < 10 && samplePosition.x() > 0
                && samplePosition.y() < 10 && samplePosition.y() > 0;
        }

        @Override
        public boolean isOccupied(Position position) {
            return state.containsKey(position);
        }

        @Override
        public Piece getPiece(Position position) {
            return state.get(position);
        }
    }
    public enum SampleSide implements Side {
        ONE, TWO;

        @Override
        public boolean isAllyWith(Side side) {
            return this.equals(side);
        }
        
    }
}
