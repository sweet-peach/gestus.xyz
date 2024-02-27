package xyz.gestus.gestus.feature.file.exception;

public class NoMoreSpaceException extends RuntimeException{
    public NoMoreSpaceException(String message){
        super(message);
    }
}
