package personal.roon.cookie.web.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ExceptionCatcher {
    @ExceptionHandler(NullPointerException.class)
    public String nullPointer(NullPointerException e) {
        log.info(e.getCause() + " " + e.getMessage());
        return e.getMessage();
    }
}
