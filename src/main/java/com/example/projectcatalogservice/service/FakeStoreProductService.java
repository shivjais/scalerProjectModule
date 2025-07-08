package com.example.projectcatalogservice.service;

import com.example.projectcatalogservice.dtos.FakeStoreProductDto;
import com.example.projectcatalogservice.models.Category;
import com.example.projectcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
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
            return Arrays.stream(fakeStoreProductDtos).map(FakeStoreProductService::from).toList();
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
            return from(fakeStoreProductDto);
        }
        return null;
    }

    private static Product from(FakeStoreProductDto fakeStoreProductDto) {
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
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = from(product);
        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.postForEntity("https://fakestoreapi.com/products", fakeStoreProductDto, FakeStoreProductDto.class);
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()){
            return from(responseEntity.getBody());
        }
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        FakeStoreProductDto fakeStoreProductRequest = from(product);
        ResponseEntity<FakeStoreProductDto> responseEntity = requestForEntity("https://fakestoreapi.com/products/{id}", fakeStoreProductRequest, HttpMethod.PUT, FakeStoreProductDto.class, id);
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()){
            return from(responseEntity.getBody());
        }
        return null;
    }

    //because putForEntity is not supported by restTemplate, we can use exchange()
    //exchange() can be used for any http method & for all endpoints (public/private)
    private <T> ResponseEntity<T> requestForEntity(String url, @Nullable Object request, HttpMethod httpMethod, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
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