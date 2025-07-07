package be.vdab.webshop.domain.dto;

import be.vdab.webshop.domain.entities.Product;

import java.math.BigDecimal;

public record ProductDto(long id, String barcode, String productname, boolean available, BigDecimal price) {
    public ProductDto(Product product) {
        this(product.getId(),  product.getBarcode(), product.getProductname(), product.getStock() > 0, product.getPrice());
    }
}
