package com.bookshop.exception;

import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomGlobalExceptionHandler{

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<String> handleEntityNotFoundException (
            EntityNotFoundException e) {
        return new ResponseEntity<>("Entity not found " + e.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<List<String>> handleValidationException (
            MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleAllException (
            Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
 }


