package be.vdab.webshop.domain.entities;

import be.vdab.webshop.exceptions.SaleHasAlreadyThisSaleItemException;
import be.vdab.webshop.exceptions.UserHasAlreadyThisProductInShopcart;
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

    @ManyToMany(mappedBy = "wishlistProducts")
    private Set<User> wishlistUsers;

    @ManyToMany (mappedBy = "shopcartProducts")
    private Set<User> shopcartUsers;

    @OneToMany(mappedBy = "product")
    private Set<SaleItem> saleItems;


    public Product(String barcode, String productname, int stock, BigDecimal price,  Prodgroup prodgroup) {
        this.barcode = barcode;
        this.productname = productname;
        this.stock = stock;
        this.price = price;
        this.prodgroup = prodgroup;
        this.wishlistUsers = new LinkedHashSet<>();
        this.saleItems = new LinkedHashSet<>();
        this.shopcartUsers = new LinkedHashSet<>();
    }

    void addSaleItem(SaleItem saleItem) {
        if (!saleItems.add(saleItem)) {
            throw new SaleHasAlreadyThisSaleItemException();
        }
    }

    public Set<SaleItem> getSaleItems() {
        return Collections.unmodifiableSet(saleItems);
    }

    void addWishlistUser(User user) {
        if (!wishlistUsers.add(user)) {
            throw new UserHasAlreadyThisProductInWishlist();
        }
    }

    void removeWishlistUser(User user) {
        wishlistUsers.remove(user);
    }

    Set<User> getWishlistUsers() {
        return Collections.unmodifiableSet(wishlistUsers);
    }

    void addShopcartUser(User user) {
        if (!shopcartUsers.add(user)) {
            throw new UserHasAlreadyThisProductInShopcart();
        }
    }

    void removeShopcartUser(User user) {
        shopcartUsers.remove(user);
    }

    Set<User> getShopcartUsers() {
        return Collections.unmodifiableSet(shopcartUsers);
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
