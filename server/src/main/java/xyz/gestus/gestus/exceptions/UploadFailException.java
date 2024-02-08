package xyz.gestus.gestus.exceptions;

public class UploadFailException extends RuntimeException{
    public UploadFailException(String message){
        super(message);
    }
}
