package xyz.gestus.gestus.core.exceptions;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<ErrorObject> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            httpStatus = HttpStatus.valueOf(statusCode);
        }

        Object error = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        String errorMessage = "Something went wrong";
        if (error != null) {
            errorMessage = error.toString();
        }

        ErrorObject errorObject = new ErrorObject();
        errorObject.setCode(httpStatus.value());
        errorObject.setMessage(errorMessage);

        return new ResponseEntity<>(errorObject, httpStatus);
    }
}
