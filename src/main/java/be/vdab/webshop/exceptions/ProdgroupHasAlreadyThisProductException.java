package be.vdab.webshop.exceptions;


//@ResponseStatus(HttpStatus.CONFLICT)
public class ProdgroupHasAlreadyThisProductException extends RuntimeException {

    public ProdgroupHasAlreadyThisProductException(String barcode) {

        super("Product with code " + barcode + " has already been added");
    }
}
