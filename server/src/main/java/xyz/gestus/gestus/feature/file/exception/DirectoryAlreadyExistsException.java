package xyz.gestus.gestus.feature.file.exception;

public class DirectoryAlreadyExistsException extends RuntimeException{
    public DirectoryAlreadyExistsException(String message){
        super(message);
    }

}
