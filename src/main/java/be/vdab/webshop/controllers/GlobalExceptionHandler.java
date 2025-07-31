package be.vdab.webshop.controllers;

import be.vdab.webshop.exceptions.UserExistsAlreadyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserExistsAlreadyException.class)
    public ResponseEntity<String> handleException(UserExistsAlreadyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
