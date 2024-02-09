package xyz.gestus.gestus.exceptions;

public class DownloadFailException extends RuntimeException{
    public DownloadFailException(String message){
        super(message);
    }
}
