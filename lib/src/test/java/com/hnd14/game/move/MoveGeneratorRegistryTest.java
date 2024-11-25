package com.hnd14.game.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hnd14.chess.exception.PieceTypeDoesNotExistException;
import com.hnd14.game.GameState;
import com.hnd14.game.Move;
import com.hnd14.game.Piece;
import com.hnd14.game.Position;
import com.hnd14.game.piece.PieceTypeManager;
import com.hnd14.game.piece.PieceTypeManager.PieceType;

class MoveGeneratorRegistryTest {
    private static PieceTypeManager pieceTypeManager;
    private static MoveGenerator  moveGenerator = new MoveGenerator() {
        @Override
        public List<Move> generateMove(GameState gameState, Position start, Piece performingPiece) {
            return List.of();
        } 
    };
    private static MoveGeneratorRegistry moveGeneratorRegistry;

    @BeforeAll
    static void setup() {
        pieceTypeManager = new PieceTypeManager();
        pieceTypeManager.register("Type1");
        pieceTypeManager.register("Type2");
        pieceTypeManager.registerAlias("T1", "Type1");
        assertNotNull(pieceTypeManager.fromString("Type1"));
        moveGeneratorRegistry = new MoveGeneratorRegistry(pieceTypeManager);
    }
    @Test
    void testGetMoveGeneratorRegistry() {
        PieceType type1 = pieceTypeManager.fromString("T1");
        assertThrows(PieceTypeDoesNotExistException.class, 
            () -> moveGeneratorRegistry.getMoveGenerator("Type1"));
        assertThrows(PieceTypeDoesNotExistException.class, 
            () -> moveGeneratorRegistry.getMoveGenerator("Type2"));
        assertThrows(PieceTypeDoesNotExistException.class, 
            () -> moveGeneratorRegistry.getMoveGenerator("T1"));
        assertThrows(PieceTypeDoesNotExistException.class, 
            () -> moveGeneratorRegistry.getMoveGenerator(type1));
        assertThrows(PieceTypeDoesNotExistException.class, 
            () -> moveGeneratorRegistry.getMoveGenerator("Type3"));
        
        // register with name
        moveGeneratorRegistry.register("Type2", moveGenerator);
        assertEquals(moveGenerator, moveGeneratorRegistry.getMoveGenerator("Type2"));
        assertEquals(moveGenerator, moveGeneratorRegistry.getMoveGenerator(pieceTypeManager.fromString("Type2")));

        // register with alias
        moveGeneratorRegistry.register("T1", moveGenerator);
        assertEquals(moveGenerator, moveGeneratorRegistry.getMoveGenerator("T1"));
        assertEquals(moveGenerator, moveGeneratorRegistry.getMoveGenerator("Type1"));
    }
}
