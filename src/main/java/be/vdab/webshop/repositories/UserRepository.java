package be.vdab.webshop.repositories;

import be.vdab.webshop.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
