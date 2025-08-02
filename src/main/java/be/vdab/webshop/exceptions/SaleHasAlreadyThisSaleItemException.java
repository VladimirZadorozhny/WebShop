package be.vdab.webshop.exceptions;

//@ResponseStatus(HttpStatus.CONFLICT)
public class SaleHasAlreadyThisSaleItemException extends RuntimeException {
    public SaleHasAlreadyThisSaleItemException() {

        super("This saleITEM has already been added");
    }
}
