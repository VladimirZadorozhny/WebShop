package be.vdab.webshop.repositories;

import be.vdab.webshop.domain.entities.Product;
import org.junit.jupiter.api.Test;

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
    public void testThatExistingProductCanBeFounded() {
        Optional<Product> testProduct = repository.findById(idOfTestProduct1());
        assertThat(testProduct).isPresent();
        assertThat(testProduct.get().getProductname()).isEqualTo("testproduct1");
        assertThat(testProduct.get().getId()).isEqualTo(idOfTestProduct1());
    }

    @Test
    public void testThatLookingForNonExistingProductGivesEmptyResult() {
        Optional<Product> testProduct = repository.findById(Long.MAX_VALUE);
        assertThat(testProduct).isEmpty();
    }

    @Test
    public void testThatFindAllProductsOfGroupnameReturnsProducts() {
        List<Product> products = repository.findAllProductsOfGroupname("testgroup1");
        assertThat(products).hasSize(1);
        Product expectedProduct = repository.findById(idOfTestProduct1())
                .orElseThrow(() -> new AssertionError("Expected product with id " + idOfTestProduct1() + " not found"));
        assertThat(products).containsExactly(expectedProduct);

    }

    @Test
    public void testThatFindAllProductsOfNonExistingGroupnameReturnsEmptyList() {
        List<Product> products = repository.findAllProductsOfGroupname("UnknownGroup");
        assertThat(products).isEmpty();

    }

}