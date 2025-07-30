package be.vdab.webshop.domain.entities;

import be.vdab.webshop.exceptions.SaleHasAlreadyThisSaleItemException;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    private LocalDateTime saletime;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private Set<SaleItem> saleItems;

    public Sale(User user) {
        this.user = user;
        this.saletime = LocalDateTime.now();
        this.saleItems = new LinkedHashSet<>();
    }

    protected Sale() {}

    void addSaleItem(SaleItem saleItem) {
        if (!saleItems.add(saleItem)) {
            throw new SaleHasAlreadyThisSaleItemException();
        }
    }

    public Set<SaleItem> getSaleItems() {
        return Collections.unmodifiableSet(saleItems);
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getSaletime() {
        return saletime;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Sale sale)) return false;
        return Objects.equals(user, sale.user) && Objects.equals(saletime, sale.saletime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, saletime);
    }
}
