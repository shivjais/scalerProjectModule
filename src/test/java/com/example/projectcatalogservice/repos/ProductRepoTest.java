package com.example.projectcatalogservice.repos;

import com.example.projectcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void findProductByPriceBetween() {
        List<Product> products = productRepo.findProductByPriceBetween(11000.0, 200000.0);
        for(Product product:products){
            System.out.println(product.getName());
        }
    }

    @Test
    public void findAllByOrderByPriceDesc() {
        List<Product> products = productRepo.findAllByOrderByPriceDesc();
        for(Product product:products){
            System.out.println(product.getName()+"->"+product.getPrice());
        }
    }

    @Test
    public void getDescriptionByProductId() {
        System.out.println(productRepo.getDescriptionByProductId(101L));
    }

}