package be.vdab.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserHasAlreadyThisSale extends RuntimeException {
    public UserHasAlreadyThisSale() {

        super("User has already this sale");
    }
}
