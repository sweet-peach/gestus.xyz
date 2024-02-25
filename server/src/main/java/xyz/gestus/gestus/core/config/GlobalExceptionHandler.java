package xyz.gestus.gestus.core.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import xyz.gestus.gestus.core.exceptions.ErrorObject;
import xyz.gestus.gestus.core.security.JwtAuthenticationFilter;
import xyz.gestus.gestus.feature.project.exception.ProjectNotFoundException;
import xyz.gestus.gestus.feature.file.exception.DirectoryAlreadyExistsException;
import xyz.gestus.gestus.feature.file.exception.DownloadFailException;
import xyz.gestus.gestus.feature.file.exception.FileNotFoundException;
import xyz.gestus.gestus.feature.file.exception.UploadFailException;
import xyz.gestus.gestus.feature.user.exception.UserAlreadyExistsException;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorObject> buildResponseEntity(HttpStatus status, Exception exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setCode(status.value());
        errorObject.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorObject, status);
    }

    private ResponseEntity<ErrorObject> buildResponseEntity(HttpStatus status, String message) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setCode(status.value());
        errorObject.setMessage(message);

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
        return buildResponseEntity(HttpStatus.CONFLICT,"File or directory already exists. Please choose a different name.");
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorObject> handleBadCredentialsException(BadCredentialsException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.UNAUTHORIZED,"Password or email is incorrect");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleNotValidArgumentException(MethodArgumentNotValidException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.BAD_REQUEST,"Invalid request format. Please check your request data.");
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleNotReadableException(HttpMessageNotReadableException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.BAD_REQUEST,"Invalid request format. Please check your request data.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObject> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.BAD_REQUEST,"Failed to convert request parameter: " + exception.getName());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorObject> handleNoResourceFoundException(NoResourceFoundException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.NOT_FOUND,"Resource not found");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorObject> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.PAYLOAD_TOO_LARGE,"Maximum upload size exceeded. Maximum file size is 100MB.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorObject> handleArgumentException(IllegalArgumentException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.BAD_REQUEST,exception);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorObject> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, WebRequest request){
        return buildResponseEntity(HttpStatus.METHOD_NOT_ALLOWED,exception);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handleAll(Exception ex, WebRequest request) {
        System.out.println("Unhandled exception");
        ex.printStackTrace();

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");
    }
}
