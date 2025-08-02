package be.vdab.webshop.exceptions;

//@ResponseStatus(HttpStatus.CONFLICT)
public class UserHasAlreadyThisProductInWishlist extends RuntimeException {
    public UserHasAlreadyThisProductInWishlist() {

        super("User has already this product in Wishlist");
    }
}
