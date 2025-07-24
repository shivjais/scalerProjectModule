package com.example.projectcatalogservice.controllers;

import com.example.projectcatalogservice.dtos.ProductDto;
import com.example.projectcatalogservice.models.Product;
import com.example.projectcatalogservice.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    //@MockBean // this is deprecated
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void TestGetAllTheProducts_RunsSuccessfully() throws Exception {
        //Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("Product 1");
        product.setPrice(10000D);
        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productService.getAllProduct()).thenReturn(products);

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Product 1");
        productDto.setDescription("Product 1");
        productDto.setPrice(10000D);
        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);


        //Act & Assert
        String expectedResponse = objectMapper.writeValueAsString(productDtos);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk()) //checking HTTP status code
                .andExpect(content().string(expectedResponse)); //checking body

        //verify how many times mocked productService.getAllProduct() is called
        verify(productService,times(1)).getAllProduct();
    }

    @Test
    public void TestCreateProduct_RunsSuccessfully() throws Exception {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Product 1");
        productDto.setDescription("Product 1");
        productDto.setPrice(10000D);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setDescription("Product 1");
        product.setPrice(10000D);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //Act & Assert
        String expectedResponse = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON) //passing content type header
                            //.header("Content-Type", MediaType.APPLICATION_JSON) //passing custom header
                            .content(objectMapper.writeValueAsString(productDto)) //Passing Request Body

                        )
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponse));
    }
}
