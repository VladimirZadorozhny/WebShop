package be.vdab.webshop.services;

import be.vdab.webshop.domain.dto.NewUserRec;
import be.vdab.webshop.domain.entities.Product;
import be.vdab.webshop.domain.entities.User;
import be.vdab.webshop.exceptions.UserExistsAlreadyException;
import be.vdab.webshop.repositories.ProductRepository;
import be.vdab.webshop.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly=true)
public class UserService {
    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public long createUser(NewUserRec userRec) {
        try {
            var user = new User(userRec.firstname(), userRec.lastname(), userRec.email(),
                    passwordEncoder.encode(userRec.password()), 1);
            user.addAuthority("user");
            userRepository.save(user);
            return user.getId();
        } catch (DataIntegrityViolationException e) {
            throw new UserExistsAlreadyException();
        }
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void refreshUserShopcartInDB(User user, Set<Long> productIds) {
        Set<Product> newProducts = new HashSet<>(productRepository.findAllById(productIds));
        user.updateShopcartProducts(newProducts);
        System.out.println("Aantal producten in winkelwagen: " + user.getShopcartProducts().size());
        userRepository.save(user);
        System.out.println("Aantal producten in winkelwagen: " + user.getShopcartProducts().size());
    }
}
