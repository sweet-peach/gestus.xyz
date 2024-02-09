package xyz.gestus.gestus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleUserAlreadyExists(UserAlreadyExistsException exception, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorObject> handleUserAlreadyExists(ProjectNotFoundException exception, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DirectoryAlreadyExistsException.class)
    public ResponseEntity<ErrorObject> handleDirectoryAlreadyExists(DirectoryAlreadyExistsException exception, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorObject> handleFileNotFound(FileNotFoundException exception, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setCode(HttpStatus.CONFLICT.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UploadFailException.class)
    public ResponseEntity<ErrorObject> handleUploadFail(UploadFailException exception, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DownloadFailException.class)
    public ResponseEntity<ErrorObject> handleUploadFail(DownloadFailException exception, WebRequest request){
        ErrorObject errorObject = new ErrorObject();

        errorObject.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
