package be.vdab.webshop.repositories;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.test.jdbc.JdbcTestUtils;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql("/prodgroups.sql")
class ProdgroupRepositoryTest {
    private static final String PRODGROUPS_TABLE = "prodgroups";
    private final  ProdgroupRepository repository;
    private final JdbcClient jdbcClient;

    @Autowired
    public ProdgroupRepositoryTest(ProdgroupRepository repository, JdbcClient jdbcClient) {
        this.repository = repository;
        this.jdbcClient = jdbcClient;
    }

@Test
    public void testThatFindAllGroupsReturnsAllGroups() {
        assertThat(repository.findAll().size()).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcClient, PRODGROUPS_TABLE));
}
}