package be.vdab.webshop.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {

        super("User with email " + email + " not found.");
    }
}
