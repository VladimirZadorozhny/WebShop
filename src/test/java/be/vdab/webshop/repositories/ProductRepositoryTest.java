package be.vdab.webshop.repositories;

import be.vdab.webshop.domain.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/prodgroups.sql", "/products.sql"})
class ProductRepositoryTest {

    private static final String PRODGROUPS_TABLE = "prodgroups";
    private static final String PRODUCTS_TABLE = "products";
    private final ProductRepository repository;
    private final JdbcClient jdbcClient;


    public ProductRepositoryTest(ProductRepository repository, JdbcClient jdbcClient) {
        this.repository = repository;
        this.jdbcClient = jdbcClient;
    }

    private long idOfTestProduct1() {
        return jdbcClient.sql("select id from products where productname = 'testproduct1'")
                .query(Long.class).single();
    }

    @Test
    public void testThatProductCanBeFounded() {
        Optional<Product> testProduct = repository.findById(idOfTestProduct1());
        assertThat(testProduct).isPresent();
        assertThat(testProduct.get().getProductname()).isEqualTo("testproduct1");
        assertThat(testProduct.get().getId()).isEqualTo(idOfTestProduct1());
    }

    @Test
    public void testThatFindAllProductsOfGroupnameReturnsProducts() {
        List<Product> products = repository.findAllProductsOfGroupname("testgroup1");
        assertThat(products).hasSize(1);
        assertThat(products).containsExactly(repository.findById(idOfTestProduct1()).get());
    }

}