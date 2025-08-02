package be.vdab.webshop.exceptions;

//@ResponseStatus(HttpStatus.CONFLICT)
public class UserHasAlreadyThisSale extends RuntimeException {
    public UserHasAlreadyThisSale() {

        super("User has already this sale");
    }
}
