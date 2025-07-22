package com.example.projectcatalogservice.controllers;

import com.example.projectcatalogservice.dtos.ProductDto;
import com.example.projectcatalogservice.models.Product;
import com.example.projectcatalogservice.service.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private IProductService productService;

    @Test
    public void TestGetProductById_ValidId_RunSuccess(){
        //Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(2000d);

        long id = 1;
        when(productService.getProductById(id)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> responseEntity = productController.getProductById(id);

        //Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(id, responseEntity.getBody().getId());
    }

    @Test
    public void TestGetProductById_InvalidId_ReturnsInIllegalArgumentException(){
        //Arrange
        long id=-1L;

        //Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productController.getProductById(id));
        //validating the exception message
        assertEquals("Invalid product ID",exception.getMessage());
    }

    @Test
    public void TestGetProductById_ServiceThrowsException_ResultsSameException(){
        //Arrange
        long id = 1l;
        when(productService.getProductById(id)).thenThrow(new RuntimeException());

        //Act
        assertThrows(RuntimeException.class, () -> productController.getProductById(id));
    }

    @Test
    public void TestCreateProduct_ValidInput_RunSuccess(){
        //Arrange
        Product product = new Product();
        product.setName("Product 2");
        product.setPrice(40000D);
        product.setId(2L);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //Act
        ProductDto productDto = new ProductDto();
        productDto.setName("Product 2");
        productDto.setPrice(40000D);
        productDto.setId(2L);
        ResponseEntity<ProductDto> responseEntity = productController.createProduct(productDto);

        //assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(productDto.getId(), responseEntity.getBody().getId());
        assertEquals(productDto.getName(), responseEntity.getBody().getName());
    }

    @Test
    public void TestCreateProduct_InvalidInput_ReturnNull(){
        //Arrange
        when(productService.createProduct(any(Product.class))).thenReturn(null);

        //Act
        ProductDto productDto = new ProductDto();
        productDto.setName("Product 2");
        productDto.setPrice(40000D);
        productDto.setId(2L);
        ResponseEntity<ProductDto> responseEntity  = productController.createProduct(productDto);
        assertNull(responseEntity);
    }

}