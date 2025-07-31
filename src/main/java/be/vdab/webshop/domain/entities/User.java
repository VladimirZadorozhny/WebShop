package be.vdab.webshop.domain.entities;

import be.vdab.webshop.exceptions.UserHasAlreadyThisAuthorityException;
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

    @ManyToMany(mappedBy = "users")
    @OrderBy("id")
    private Set<Product> products;

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
        this.products = new LinkedHashSet<>();
        this.sales = new LinkedHashSet<>();
    }

    public void addSale(Sale sale) {
        if (!sales.add(sale)) {
            throw new UserHasAlreadyThisSale();
        }
    }

    public Set<Sale> getSales() {
        return Collections.unmodifiableSet(sales);
    }

    public void addProduct(Product product) {
        if (!products.add(product)) {
            throw new UserHasAlreadyThisAuthorityException();
        }
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
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
