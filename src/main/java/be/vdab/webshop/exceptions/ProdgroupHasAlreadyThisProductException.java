package be.vdab.webshop.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProdgroupHasAlreadyThisProductException extends RuntimeException {

    public ProdgroupHasAlreadyThisProductException(String barcode) {

        super("Product with code " + barcode + " has already been added");
    }
}
