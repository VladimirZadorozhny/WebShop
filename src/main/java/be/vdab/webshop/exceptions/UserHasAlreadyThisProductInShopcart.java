package be.vdab.webshop.exceptions;

//@ResponseStatus(HttpStatus.CONFLICT)
public class UserHasAlreadyThisProductInShopcart extends RuntimeException {
    public UserHasAlreadyThisProductInShopcart() {

        super("User has already this product in Shop cart.");
    }
}
