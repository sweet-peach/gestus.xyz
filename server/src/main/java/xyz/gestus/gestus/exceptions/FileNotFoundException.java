package xyz.gestus.gestus.exceptions;

public class FileNotFoundException extends RuntimeException{
    public FileNotFoundException(String message){
        super(message);
    }
}
