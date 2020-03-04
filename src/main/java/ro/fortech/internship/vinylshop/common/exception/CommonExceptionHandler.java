package ro.fortech.internship.vinylshop.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = InvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handler(InvalidException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = InvalidPasswordOrEmailException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected String handler(InvalidPasswordOrEmailException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userNotFound(ResourceNotFoundException ex) {
        return ex.getMessage();
    }
}
