package be.vdab.webshop.domain.entities;

import be.vdab.webshop.exceptions.ProdgroupHasAlreadyThisProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProdgroupTest {
    private Product product;
    private Prodgroup prodgroup;

    @BeforeEach
    void setup() {
        prodgroup = new Prodgroup("testprodgroup1");
        product = new Product("11111", "testproduct1", 10, BigDecimal.valueOf(15.5), prodgroup);
    }

    @Test
    void testThatAddTheSameProductToProdgroupThrowsException() {
        prodgroup.addProduct(product);
        assertThatExceptionOfType(ProdgroupHasAlreadyThisProductException.class).isThrownBy(() -> prodgroup.addProduct(product));
    }

    @Test
    void testThatProdgroupContainsTheAddedProduct() {
        prodgroup.addProduct(product);
        assertThat(prodgroup.getProducts()).containsExactly(product);
    }


}