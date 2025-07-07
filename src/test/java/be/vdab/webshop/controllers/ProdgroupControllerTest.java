package be.vdab.webshop.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Sql({"/prodgroups.sql", "/products.sql"})
class ProdgroupControllerTest {
private final static String PRODGROUPS_TABLE = "prodgroups";
private static final String PRODUCTS_TABLE = "products";

private final MockMvcTester mockMvc;
private final JdbcClient jdbcClient;


    ProdgroupControllerTest(MockMvcTester mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
    }

    private long idOfTestProdgroup1() {
        return jdbcClient.sql("select id from prodgroups where groupname = 'testgroup1'")
                .query(Long.class).single();
    }

    private List<String> productsOfTestProduct1(long id) {
        return jdbcClient.sql("select barcode from products where groupId = ?").param(id).query(String.class).list();
        }

    @Test
    public void testThatGetProdgroupsIdNameReturnsAllGroupsSorted() {
        var response = mockMvc.get().uri("/prodgroups");
        assertThat(response)
                .hasStatusOk()
                .bodyJson()
                .satisfies(json -> assertThat(json).extractingPath("length()")
                                .isEqualTo(JdbcTestUtils.countRowsInTable(jdbcClient, PRODGROUPS_TABLE)),
                        json -> assertThat(json).extractingPath("[*].groupname")
                                .asArray()
                                .isSortedAccordingTo(Comparator.comparing(String::valueOf))
                );
    }

    @Test
    public void testThatGetProductByGroupIdReturnsProductsOfExistingGroup() {
        var id = idOfTestProdgroup1();
        var products = productsOfTestProduct1(id);
        var response = mockMvc.get().uri("/prodgroups/{id}/products", id);
        assertThat(response)
                .hasStatusOk()
                .bodyJson()
                .satisfies(json -> assertThat(json).extractingPath("length()")
                                .isEqualTo(JdbcTestUtils.countRowsInTableWhere(jdbcClient, PRODUCTS_TABLE,
                                        "groupId = " + id)),
                        json -> assertThat(json).extractingPath("[*].barcode")
                                .isEqualTo(products));

    }

    @Test
    public void testThatGetProductByGroupIdReturnsNotFoundOfNonExistingGroup() {
        var response = mockMvc.get().uri("/prodgroups/{id}/products", Long.MAX_VALUE);
        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
    }

}