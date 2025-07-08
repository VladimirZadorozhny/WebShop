package be.vdab.webshop.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Sql({"/prodgroups.sql", "/products.sql"})
class ProductControllerTest {
    private static final String PRODUCTS_TABLE = "products";

    private final MockMvcTester mockMvc;
    private final JdbcClient jdbcClient;

    public ProductControllerTest(MockMvcTester mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;

        this.jdbcClient = jdbcClient;
    }

    private long idOfTestProduct1() {
        return jdbcClient.sql("select id from products where productname = 'testproduct1'")
                .query(Long.class).single();
    }

    @Test
    public void testThatGetProductByIdOfNonexistingProductReturnsCode404NotFound() {
        var response = mockMvc.get().uri("/products/{id}", Long.MAX_VALUE);
        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testThatGetProductByIdOfExistingProductReturnsCode200() {

        var response = mockMvc.get().uri("/products/{id}", idOfTestProduct1());
        assertThat(response).hasStatusOk();
    }

    @Test
    public void testThatGetProductByIdOfExistingProductReturnsProduct() {
        var id = idOfTestProduct1();
        var response = mockMvc.get().uri("/products/{id}", id);
        assertThat(response)
                .bodyJson()
                .extractingPath("barcode").isEqualTo(jdbcClient
                        .sql("select barcode from products where id = ?").param(id)
                        .query(String.class).single());
        }

}