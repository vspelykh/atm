package ua.vspelykh.atm.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.vspelykh.atm.util.exception.ServiceException;

/**
 * Global exception handler for REST controllers.
 *
 * @version 1.0
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exceptions to type ServiceException.
     *
     * @param ex The ServiceException that was thrown.
     * @return A ProblemDetail response with the appropriate HTTP status code and error detail.
     */
    @ExceptionHandler(ServiceException.class)
    public ProblemDetail handleWithdrawException(ServiceException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

}
