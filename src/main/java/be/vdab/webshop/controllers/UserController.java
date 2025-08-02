package be.vdab.webshop.controllers;

import be.vdab.webshop.domain.dto.NewUserRec;

import be.vdab.webshop.domain.dto.ProductDto;
import be.vdab.webshop.exceptions.UserNotFoundException;
import be.vdab.webshop.services.UserService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid NewUserRec user) {
            long id = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("{email}/shopcart")
    public Stream<ProductDto> getShopcart(@PathVariable("email") String email) {
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email))
                .getShopcartProducts()
                .stream()
                .map(ProductDto::new);
    }

    @PutMapping("{email}/shopcart")
    public ResponseEntity<?> updateShopcart(@PathVariable("email") String email, @RequestBody Set<Long> productIds) {
        var user = userService.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        userService.refreshUserShopcartInDB(user, productIds);
        return ResponseEntity.ok().build();
    }
}
