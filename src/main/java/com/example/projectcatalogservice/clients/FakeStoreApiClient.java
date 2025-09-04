package com.example.projectcatalogservice.clients;

import com.example.projectcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.util.List;

//@Component is base layer for @Controller, @RestController, @Service, @Repo
//it helps to register this class as spring bean
@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private RestClient restClient;

    /*public List<FakeStoreProductDto> getAllProduct() {

        ResponseEntity<FakeStoreProductDto[]> responseEntity = requestForEntity("https://fakestoreapi.com/products", null, HttpMethod.GET, FakeStoreProductDto[].class);
        if(responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()){
            FakeStoreProductDto[] fakeStoreProductDtos = responseEntity.getBody();
            assert fakeStoreProductDtos != null;
            return List.of(fakeStoreProductDtos);
        }
        return null;
    }*/

    //Calling fakestore API using rest client
    public List<FakeStoreProductDto> getAllProduct() {
        FakeStoreProductDto[] fakeStoreProductDtos = restClient.get()
                .uri("https://fakestoreapi.com/products")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(FakeStoreProductDto[].class);
        return List.of(fakeStoreProductDtos);
    }

    //This will only return the body of 3rd party API
    //but to validate the other parameter like status & body we have to use getForEntity()
    //FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/{productId}",FakeStoreProductDto.class,productId);
    //ResponseEntity<FakeStoreProductDto> fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}",FakeStoreProductDto.class,productId);
    public FakeStoreProductDto getProductById(Long productId) {
        ResponseEntity<FakeStoreProductDto> responseEntity = requestForEntity("https://fakestoreapi.com/products/{productId}",null,HttpMethod.GET, FakeStoreProductDto.class, productId);
        if(validateResponse(responseEntity))
            return responseEntity.getBody();
        else
            return null;
    }

    public FakeStoreProductDto updateProduct(Long productId, FakeStoreProductDto fakeStoreProductRequest) {
        ResponseEntity<FakeStoreProductDto> responseEntity = requestForEntity("https://fakestoreapi.com/products/{productId}", fakeStoreProductRequest, HttpMethod.PUT, FakeStoreProductDto.class, productId);
        if(validateResponse(responseEntity))
            return responseEntity.getBody();
        else
            return null;
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductRequest) {
        ResponseEntity<FakeStoreProductDto> responseEntity = requestForEntity("https://fakestoreapi.com/products",fakeStoreProductRequest,HttpMethod.POST,FakeStoreProductDto.class);
        if(validateResponse(responseEntity))
            return responseEntity.getBody();
        else
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

    private boolean validateResponse(ResponseEntity<FakeStoreProductDto>  responseEntity) {
        return responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody();
    }
}
