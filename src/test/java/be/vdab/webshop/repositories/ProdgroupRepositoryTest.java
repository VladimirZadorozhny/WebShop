package be.vdab.webshop.repositories;

import org.junit.jupiter.api.Test;
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


    public ProdgroupRepositoryTest(ProdgroupRepository repository, JdbcClient jdbcClient) {
        this.repository = repository;
        this.jdbcClient = jdbcClient;
    }

    private long idOfTestProdgroup1() {
        return jdbcClient.sql("select id from prodgroups where groupname = 'testgroup1'")
                .query(Long.class).single();
    }

    @Test
    public void testThatFindAllGroupsReturnsAllGroups() {
        assertThat(repository.findAll().size()).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcClient, PRODGROUPS_TABLE));
    }

    @Test
    public void testThatFindGroupByIdExistingGroupReturnsGroup() {
        var group = repository.findById(idOfTestProdgroup1());
        assertThat(group).isNotNull();
        assertThat(group).isNotEmpty();
        assertThat(group).hasValueSatisfying(prodgroup -> assertThat(prodgroup.getGroupname()).isEqualTo("testgroup1"));
    }

    @Test
    public void testThatFindGroupByIdNonExistingGroupReturnsNull() {
        final var group = repository.findById(Long.MAX_VALUE);
        assertThat(group).isEmpty();
    }

}