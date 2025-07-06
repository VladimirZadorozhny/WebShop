package be.vdab.webshop.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Sql("/prodgroups.sql")
class ProdgroupControllerTest {
private final static String PRODGROUPS_TABLE = "prodgroups";

private final MockMvcTester mockMvc;
private final JdbcClient jdbcClient;


    ProdgroupControllerTest(MockMvcTester mockMvc, JdbcClient jdbcClient) {
        this.mockMvc = mockMvc;
        this.jdbcClient = jdbcClient;
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

}