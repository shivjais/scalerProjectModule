package com.example.projectcatalogservice.service;

import com.example.projectcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProduct();

    Product getProductById(Long id);

    Product createProduct(Product product);

    Product updateProduct(Product product, Long id);

    Product getProductByUserId(Long productId, Long userId);
}
