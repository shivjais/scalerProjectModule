package com.example.projectcatalogservice.service;

import com.example.projectcatalogservice.dtos.FakeStoreProductDto;
import com.example.projectcatalogservice.models.Category;
import com.example.projectcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeStoreProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Product> getAllProduct() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> responseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()){
            FakeStoreProductDto[] fakeStoreProductDtos = responseEntity.getBody();
            return Arrays.stream(fakeStoreProductDtos).map(FakeStoreProductService::getProduct).toList();
        }
        return null;
    }

    @Override
    public Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //This will only return the body of 3rd party API
        //but to validate the other parameter like status & body we have to use getForEntity()
        //FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/{productId}",FakeStoreProductDto.class,productId);

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}", FakeStoreProductDto.class, productId);
        if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
            FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();
            //convert FakeStoreProductDto to product
            return getProduct(fakeStoreProductDto);
        }
        return null;
    }

    private static Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
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

    @Override
    public Product createProduct(Product product) {
        return null;
    }
}