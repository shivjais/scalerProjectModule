package com.example.projectcatalogservice.service;

import com.example.projectcatalogservice.clients.FakeStoreApiClient;
import com.example.projectcatalogservice.dtos.FakeStoreProductDto;
import com.example.projectcatalogservice.models.Category;
import com.example.projectcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Primary
public class FakeStoreProductService implements IProductService{

    @Autowired
    private FakeStoreApiClient  fakeStoreApiClient;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<Product> getAllProduct() {
        List<FakeStoreProductDto> allProduct = fakeStoreApiClient.getAllProduct();
        if(allProduct==null)
            return null;
        return allProduct.stream().map(this::from).toList();
    }

    @Override
    public Product getProductById(Long productId) {
        //if found in cache return it else get it from fakestore
        FakeStoreProductDto fakeStoreProduct;
        fakeStoreProduct = (FakeStoreProductDto) redisTemplate.opsForHash().get("PRODUCTS",productId);

        if(fakeStoreProduct==null){
            System.out.println("product found from fake store");
            fakeStoreProduct = fakeStoreApiClient.getProductById(productId);
            if (fakeStoreProduct == null) return null;
            //store it in cache
            redisTemplate.opsForHash().put("PRODUCTS",productId,fakeStoreProduct);
        }
        else{
            System.out.println("product found from Redis");
        }
        //convert FakeStoreProductDto to product
        return from(fakeStoreProduct);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreRequest = from(product);
        FakeStoreProductDto createdProduct = fakeStoreApiClient.createProduct(fakeStoreRequest);
        if(createdProduct==null)
            return null;
        return from(createdProduct);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        FakeStoreProductDto fakeStoreProductRequest = from(product);
        FakeStoreProductDto updatedProduct = fakeStoreApiClient.updateProduct(id, fakeStoreProductRequest);
        if(updatedProduct==null)
            return null;
        return from(updatedProduct);
    }

    @Override
    public Product getProductByUserId(Long productId, Long userId) {
        return null;
    }


    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }
}