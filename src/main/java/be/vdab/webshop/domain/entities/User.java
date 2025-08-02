package be.vdab.webshop.domain.entities;

import be.vdab.webshop.exceptions.UserHasAlreadyThisAuthorityException;
import be.vdab.webshop.exceptions.UserHasAlreadyThisProductInShopcart;
import be.vdab.webshop.exceptions.UserHasAlreadyThisProductInWishlist;
import be.vdab.webshop.exceptions.UserHasAlreadyThisSale;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int enabled;

    @ElementCollection
    @CollectionTable(name = "authorities",
    joinColumns = @JoinColumn(name = "username", referencedColumnName = "email"))
    @Column(name = "authority")
    private Set<String> authorities;

    @ManyToMany
    @JoinTable(
            name = "wishlists",
            joinColumns = @JoinColumn(name = "productId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<Product> wishlistProducts;

    @ManyToMany
    @JoinTable(
            name = "shopcarts",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private Set<Product> shopcartProducts;

    @OneToMany (mappedBy = "user")
    @OrderBy("saletime")
    private Set<Sale> sales;

    protected User() {}

    public User(String firstname, String lastname, String email, String password, int enabled) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = new LinkedHashSet<>();
        this.wishlistProducts = new LinkedHashSet<>();
        this.sales = new LinkedHashSet<>();
        this.shopcartProducts = new LinkedHashSet<>();
    }

    public void addSale(Sale sale) {
        if (!sales.add(sale)) {
            throw new UserHasAlreadyThisSale();
        }
    }

    public Set<Sale> getSales() {
        return Collections.unmodifiableSet(sales);
    }

    public void addWishlistProduct(Product product) {
        if (!wishlistProducts.add(product)) {
            throw new UserHasAlreadyThisProductInWishlist();
        }
    }

    public void removeWishlistProduct(Product product) {
        wishlistProducts.remove(product);
    }

    public Set<Product> getWishlistProducts() {
        return Collections.unmodifiableSet(wishlistProducts);
    }

    public void addShopcartProduct(Product product) {
        if (!shopcartProducts.add(product)) {
            throw new UserHasAlreadyThisProductInShopcart();
        }
    }

    public void updateShopcartProducts(Set<Product> newProducts) {

        shopcartProducts.removeIf(product -> !newProducts.contains(product));
        shopcartProducts.addAll(newProducts);
//        newProducts.forEach(this::addShopcartProduct);
//        shopcartProducts.addAll(newProducts);
    }

    public void removeShopcartProduct(Product product) {
        shopcartProducts.remove(product);
    }

    public Set<Product> getShopcartProducts() {
        return Collections.unmodifiableSet(shopcartProducts);
    }

    public void addAuthority(String authority) {
        if (!authorities.add(authority)) {
            throw new UserHasAlreadyThisAuthorityException();
        }
    }

    public void removeAuthority(String authority) {
        authorities.remove(authority);
    }

    public Set<String> getAuthorities() {
        return Collections.unmodifiableSet(authorities);
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
