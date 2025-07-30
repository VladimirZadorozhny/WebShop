package be.vdab.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserHasAlreadyThisAuthorityException extends RuntimeException {
    public UserHasAlreadyThisAuthorityException() {

        super("User has already this authority");
    }
}
