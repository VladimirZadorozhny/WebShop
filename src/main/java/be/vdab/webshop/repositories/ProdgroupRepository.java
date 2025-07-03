package be.vdab.webshop.repositories;

import be.vdab.webshop.domain.entities.Prodgroup;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProdgroupRepository extends JpaRepository<Prodgroup, Long> {
}
