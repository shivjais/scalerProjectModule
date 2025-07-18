package com.example.projectcatalogservice.service;

import com.example.projectcatalogservice.models.Product;
import com.example.projectcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary //Making this class as primary bean implementation so that
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProd = productRepo.findById(id);
        //return optionalProd.orElse(null);
        if (optionalProd.isPresent()) {
            return optionalProd.get();
        }
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        //save method is override the existing product. so we need to check if new product already exist or not
        //if does, return old product otherwise create it
        Product oldProduct = getProductById(product.getId());
        if (oldProduct != null) {
            return oldProduct;
        }
        return productRepo.save(product); //save the product
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Product oldProduct = getProductById(id);
        if (oldProduct == null) {
            throw new IllegalArgumentException("Product not found");
        }
        return productRepo.save(product); //this will update existing product
    }
}
