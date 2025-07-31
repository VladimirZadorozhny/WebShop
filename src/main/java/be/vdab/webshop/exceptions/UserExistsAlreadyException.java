package be.vdab.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsAlreadyException extends RuntimeException {
    public UserExistsAlreadyException() {

        super("User with this email already exists");
    }
}
