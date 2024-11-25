package com.hnd14.game.move;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hnd14.chess.exception.PieceTypeDoesNotExistException;
import com.hnd14.game.piece.PieceTypeManager;
import com.hnd14.game.piece.PieceTypeManager.PieceType;

public class MoveGeneratorRegistry {
    private PieceTypeManager pieceTypeManager;
    private Map<PieceType, MoveGenerator> registry = new ConcurrentHashMap<>();
    public MoveGeneratorRegistry(PieceTypeManager pieceTypeManager) {
        this.pieceTypeManager = pieceTypeManager;
    }

    public synchronized void register(String typeName, MoveGenerator moveGenerator) {
        registry.put(pieceTypeManager.fromString(typeName), moveGenerator);
    }

    public MoveGenerator getMoveGenerator(String typeName) {
        MoveGenerator moveGenerator = registry.get(pieceTypeManager.fromString(typeName));
        if (moveGenerator == null) {
            throw new PieceTypeDoesNotExistException(typeName);
        }
        return moveGenerator;
    } 

    public MoveGenerator getMoveGenerator(PieceType pieceType) {
        MoveGenerator moveGenerator = registry.get(pieceType);
        if (moveGenerator == null) {
            throw new PieceTypeDoesNotExistException(pieceType.getName());
        }
        return moveGenerator;
    } 
}
