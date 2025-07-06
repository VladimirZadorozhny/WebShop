package be.vdab.webshop.repositories;

import be.vdab.webshop.domain.dto.GroupOnlyIdName;
import be.vdab.webshop.domain.entities.Prodgroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProdgroupRepository extends JpaRepository<Prodgroup, Long> {

    @Query("""
select p.id as id, p.groupname as groupname from Prodgroup p
order by p.groupname
""")
    List<GroupOnlyIdName>  getProdgroupsIdName();
}
