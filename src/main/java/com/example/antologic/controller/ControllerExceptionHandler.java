package com.example.antologic.controller;

import com.example.antologic.common.AlreadyExistsException;
import com.example.antologic.common.ErrorMessage;
import com.example.antologic.common.NoContentException;
import com.example.antologic.common.NotFoundException;
import com.example.antologic.common.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
@Slf4j
@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An error occurred processing request" + ex);
        return message;
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage unauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An error occurred processing request" + ex);
        return message;
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage alreadyExistsException(AlreadyExistsException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An error occurred processing request" + ex);
        return message;
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage noContentException(NoContentException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        log.error("An error occurred processing request" + ex);
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        log.info("An error occurred processing request" + ex);
        return message;
    }
}
