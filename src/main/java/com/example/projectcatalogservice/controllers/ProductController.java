package com.example.projectcatalogservice.controllers;

import com.example.projectcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("iphone");

        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") Long productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return product;
    }

    @PutMapping("/product")
    public Product updateProduct(@RequestBody Product product) {
        Product updatedProduct = new Product();
        updatedProduct.setId(product.getId());
        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPrice(product.getPrice());
        return updatedProduct;
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        return "Product deleted having id: "+productId;
    }
}
