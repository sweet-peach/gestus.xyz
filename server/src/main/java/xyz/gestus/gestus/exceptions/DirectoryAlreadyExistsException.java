package xyz.gestus.gestus.exceptions;

public class DirectoryAlreadyExistsException extends RuntimeException{
    public DirectoryAlreadyExistsException(String message){
        super(message);
    }

}
