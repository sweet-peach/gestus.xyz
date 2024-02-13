package xyz.gestus.gestus.feature.file.exception;

public class DownloadFailException extends RuntimeException{
    public DownloadFailException(String message){
        super(message);
    }
}
