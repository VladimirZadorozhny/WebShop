package be.vdab.webshop.controllers;

import be.vdab.webshop.exceptions.*;
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

    @ExceptionHandler(ProdgroupHasAlreadyThisProductException.class)
    public ResponseEntity<String> handleException(ProdgroupHasAlreadyThisProductException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProdgroupNotFoundException.class)
    public ResponseEntity<String> handleException(ProdgroupNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SaleHasAlreadyThisSaleItemException.class)
    public ResponseEntity<String> handleException(SaleHasAlreadyThisSaleItemException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserHasAlreadyThisAuthorityException.class)
    public ResponseEntity<String> handleException(UserHasAlreadyThisAuthorityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserHasAlreadyThisProductInWishlist.class)
    public ResponseEntity<String> handleException(UserHasAlreadyThisProductInWishlist ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserHasAlreadyThisProductInShopcart.class)
    public ResponseEntity<String> handleException(UserHasAlreadyThisProductInShopcart ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(UserHasAlreadyThisSale.class)
    public ResponseEntity<String> handleException(UserHasAlreadyThisSale ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
