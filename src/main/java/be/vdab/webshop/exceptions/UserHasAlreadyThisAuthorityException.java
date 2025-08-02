package be.vdab.webshop.exceptions;

//@ResponseStatus(HttpStatus.CONFLICT)
public class UserHasAlreadyThisAuthorityException extends RuntimeException {
    public UserHasAlreadyThisAuthorityException() {

        super("User has already this authority");
    }
}
