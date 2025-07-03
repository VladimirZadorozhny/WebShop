package be.vdab.webshop.repositories;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;


//@DataJpaTest
@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureJdbc
@Sql("/prodgroups.sql")
class ProdgroupRepositoryTest {
    private static final String PRODGROUPS_TABLE = "prodgroups";
    private final  ProdgroupRepository repository;
    private final JdbcClient jdbcClient;


    public ProdgroupRepositoryTest(ProdgroupRepository repository, JdbcClient jdbcClient) {
        this.repository = repository;
        this.jdbcClient = jdbcClient;
    }

@Test
    public void testThatFindAllGroupsReturnsAllGroups() {
        assertThat(repository.findAll().size()).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcClient, PRODGROUPS_TABLE));
}
}