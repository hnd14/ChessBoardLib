package com.hnd14.chess.exception;

public class FileNotExistException extends RuntimeException{
    public FileNotExistException(char c){
        super(String.format("File %c does not exists in the board!", c));
    }

    public FileNotExistException(){
        super("File does not exists in the board!");
    }
}
