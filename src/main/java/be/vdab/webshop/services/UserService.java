package be.vdab.webshop.services;

import be.vdab.webshop.domain.dto.NewUserRec;
import be.vdab.webshop.domain.entities.User;
import be.vdab.webshop.exceptions.UserExistsAlreadyException;
import be.vdab.webshop.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
}
