package be.vdab.webshop.domain.entities;

import be.vdab.webshop.exceptions.SaleHasAlreadyThisSaleItemException;
import be.vdab.webshop.exceptions.UserHasAlreadyThisProductInWishlist;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

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

    @ManyToMany
    @JoinTable(
            name = "wishlists",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> users;

    @OneToMany(mappedBy = "product")
    private Set<SaleItem> saleItems;


    public Product(String barcode, String productname, int stock, BigDecimal price,  Prodgroup prodgroup) {
        this.barcode = barcode;
        this.productname = productname;
        this.stock = stock;
        this.price = price;
        this.prodgroup = prodgroup;
        this.users = new LinkedHashSet<>();
        this.saleItems = new LinkedHashSet<>();
    }

    void addSaleItem(SaleItem saleItem) {
        if (!saleItems.add(saleItem)) {
            throw new SaleHasAlreadyThisSaleItemException();
        }
    }

    public Set<SaleItem> getSaleItems() {
        return Collections.unmodifiableSet(saleItems);
    }

    void addUser(User user) {
        if (!users.add(user)) {
            throw new UserHasAlreadyThisProductInWishlist();
        }
    }

    void removeUser(User user) {
        users.remove(user);
    }

    Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
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
