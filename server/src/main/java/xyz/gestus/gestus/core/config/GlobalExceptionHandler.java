package xyz.gestus.gestus.core.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import xyz.gestus.gestus.core.ErrorObject;
import xyz.gestus.gestus.feature.project.exception.ProjectNotFoundException;
import xyz.gestus.gestus.feature.project.file.exception.DirectoryAlreadyExistsException;
import xyz.gestus.gestus.feature.project.file.exception.DownloadFailException;
import xyz.gestus.gestus.feature.project.file.exception.FileNotFoundException;
import xyz.gestus.gestus.feature.project.file.exception.UploadFailException;
import xyz.gestus.gestus.core.user.exception.UserAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorObject> buildResponseEntity(HttpStatus status, Exception exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setCode(status.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorObject, status);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleUserAlreadyExists(UserAlreadyExistsException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.CONFLICT,exception);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserAlreadyExists(ProjectNotFoundException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.NOT_FOUND,exception);

    }

    @ExceptionHandler(DirectoryAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleDirectoryAlreadyExists(DirectoryAlreadyExistsException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.CONFLICT,exception);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorObject> handleFileNotFound(FileNotFoundException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.NOT_FOUND,exception);
    }

    @ExceptionHandler(UploadFailException.class)
    public ResponseEntity<ErrorObject> handleUploadFail(UploadFailException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,exception);
    }

    @ExceptionHandler(DownloadFailException.class)
    public ResponseEntity<ErrorObject> handleUploadFail(DownloadFailException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleAll(Exception ex, WebRequest request) {
        System.out.println("Unhandled exception");
        ex.printStackTrace();

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
}
