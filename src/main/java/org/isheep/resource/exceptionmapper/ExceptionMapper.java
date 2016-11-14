package org.isheep.resource.exceptionmapper;

import org.isheep.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by anthony on 14/11/16.
 */
@ControllerAdvice
class ExceptionMapper {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleNotFound(final HttpServletRequest req, final ResourceNotFoundException e) {
        final ErrorMessage err = ErrorMessage.builder()
                .exception(e)
                .request(req)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(final HttpServletRequest req, final IllegalArgumentException e) {
        final ErrorMessage err = ErrorMessage.builder()
                .exception(e)
                .request(req)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

}
