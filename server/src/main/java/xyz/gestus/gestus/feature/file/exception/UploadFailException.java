package xyz.gestus.gestus.feature.file.exception;

public class UploadFailException extends RuntimeException{
    public UploadFailException(String message){
        super(message);
    }
}
