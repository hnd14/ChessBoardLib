package com.hnd14.game.piece;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.hnd14.chess.exception.PieceTypeDoesNotExistException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

public class PieceTypeManager {
    @EqualsAndHashCode
    public class PieceType {
        @Getter
        private String name;
        private PieceType(String name) {
            this.name = name;
        }
    }
    
    private Map<String, PieceType> availableTypes = new ConcurrentHashMap<>();
    public PieceType fromString(String name){
        return Optional.ofNullable(availableTypes.get(name))
            .orElseThrow(() -> new PieceTypeDoesNotExistException(name));
    }

    public synchronized void register(String name){
        availableTypes.put(name, new PieceType(name));
    }

    public synchronized void registerAlias (String alias, String typeName) {
        PieceType type = availableTypes.get(typeName);
        if (type == null) {
            throw new PieceTypeDoesNotExistException(typeName);
        }
        availableTypes.put(alias, type);
    }
}
