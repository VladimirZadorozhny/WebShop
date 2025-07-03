package be.vdab.webshop.domain.entities;

import be.vdab.webshop.exceptions.ProdgroupHasAlreadyThisProductException;
import jakarta.persistence.*;

import java.util.Collections;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "prodgroups")
public class Prodgroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String groupname;

    @OneToMany(mappedBy = "prodgroup")
    @OrderBy("id")
    private Set<Product> products;

    public Prodgroup(String groupname) {
        this.groupname = groupname;
        this.products = new LinkedHashSet<>();
    }



    protected Prodgroup() {}

    public long getId() {
        return id;
    }

    public String getGroupname() {
        return groupname;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

    void addProduct(Product product) {
        if (!products.add(product)) {
            throw new ProdgroupHasAlreadyThisProductException(product.getBarcode());
        }

    }
}
