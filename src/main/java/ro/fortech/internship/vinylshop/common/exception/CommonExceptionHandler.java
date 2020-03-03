package ro.fortech.internship.vinylshop.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = InvalidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handler(InvalidException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected String handler(AuthenticationException ex) {
        return ex.getMessage();
    }
}
