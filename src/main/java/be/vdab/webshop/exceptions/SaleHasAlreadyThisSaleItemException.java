package be.vdab.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SaleHasAlreadyThisSaleItemException extends RuntimeException {
    public SaleHasAlreadyThisSaleItemException() {

        super("This saleITEM has already been added");
    }
}
