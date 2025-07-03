package be.vdab.webshop.domain.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String barcode;
    private String productname;
    private int stock;
    private BigDecimal price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Prodgroup prodgroup;


    public Product(String barcode, String productname, int stock, BigDecimal price,  Prodgroup prodgroup) {
        this.barcode = barcode;
        this.productname = productname;
        this.stock = stock;
        this.price = price;
        this.prodgroup = prodgroup;
    }

    protected Product() {}

    public long getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getProductname() {
        return productname;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Prodgroup getProdgroup() {
        return prodgroup;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return barcode.equals(product.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(barcode);
    }
}
