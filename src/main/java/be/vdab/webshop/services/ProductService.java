package be.vdab.webshop.services;

import be.vdab.webshop.domain.entities.Product;
import be.vdab.webshop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

}
