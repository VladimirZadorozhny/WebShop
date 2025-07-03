package be.vdab.webshop.repositories;


import be.vdab.webshop.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
select p from Product p where p.prodgroup.groupname = :groupname
""")
    List<Product> findAllProductsOfGroupname(@Param("groupname") String groupname);
}
