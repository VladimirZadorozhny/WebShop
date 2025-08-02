package be.vdab.webshop.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProdgroupNotFoundException extends RuntimeException {
    public ProdgroupNotFoundException(long id) {
        super("Product group with id: " + id + " not found");
    }
}
