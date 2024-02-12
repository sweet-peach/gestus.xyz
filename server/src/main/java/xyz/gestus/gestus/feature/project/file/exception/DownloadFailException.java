package xyz.gestus.gestus.feature.project.file.exception;

public class DownloadFailException extends RuntimeException{
    public DownloadFailException(String message){
        super(message);
    }
}
