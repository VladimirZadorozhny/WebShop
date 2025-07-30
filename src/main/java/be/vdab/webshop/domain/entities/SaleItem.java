package be.vdab.webshop.domain.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "saleitems")
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "saleId")
    private Sale sale;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    private BigDecimal price;

    public SaleItem(Sale sale, Product product, BigDecimal price) {
        this.sale = sale;
        this.product = product;
        this.price = price;
    }

    protected SaleItem() {}

    public long getId() {
        return id;
    }

    public Sale getSale() {
        return sale;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SaleItem saleItem)) return false;
        return Objects.equals(sale, saleItem.sale) && Objects.equals(product, saleItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, product);
    }
}
