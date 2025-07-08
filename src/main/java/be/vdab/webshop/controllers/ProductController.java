package be.vdab.webshop.controllers;

import be.vdab.webshop.domain.dto.ProductDto;
import be.vdab.webshop.exceptions.ProductNotFoundException;
import be.vdab.webshop.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    public ProductDto getProductById(@PathVariable("id") long id) {
        return productService.getProductById(id)
                .map(ProductDto::new)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
