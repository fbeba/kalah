package com.bbt.kalah.application;

import com.bbt.kalah.exception.GameAlreadyFinishedException;
import com.bbt.kalah.exception.GameDoesNotExistException;
import com.bbt.kalah.exception.IllegalMoveException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            IllegalMoveException.class,
            GameAlreadyFinishedException.class,
            GameDoesNotExistException.class })
    protected ResponseEntity<Object> handleGameException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class })
    protected ResponseEntity<Object> handleInputException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}