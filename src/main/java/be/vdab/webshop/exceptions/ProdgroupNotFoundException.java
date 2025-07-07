package be.vdab.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProdgroupNotFoundException extends RuntimeException {
    public ProdgroupNotFoundException(long id) {
        super("Product group with id: " + id + " not found");
    }
}
