package xyz.gestus.gestus.feature.project.file.exception;

public class DirectoryAlreadyExistsException extends RuntimeException{
    public DirectoryAlreadyExistsException(String message){
        super(message);
    }

}
