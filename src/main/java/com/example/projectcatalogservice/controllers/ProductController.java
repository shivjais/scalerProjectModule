package com.example.projectcatalogservice.controllers;

import com.example.projectcatalogservice.dtos.CategoryDto;
import com.example.projectcatalogservice.dtos.ProductDto;
import com.example.projectcatalogservice.models.Category;
import com.example.projectcatalogservice.models.Product;
import com.example.projectcatalogservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<Product> products = productService.getAllProduct();
        if(!products.isEmpty()){
            List<ProductDto> productDtos = products.stream().map(this::getProductDto).toList();
            return new ResponseEntity<>(productDtos,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        //it's better to add validation in controller
        if(productId<=0){
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw new IllegalArgumentException("Invalid product ID");
        }
        Product product = productService.getProductById(productId);
        if(product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ProductDto productDto = getProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = from(productDto);
        Product createdProduct = productService.createProduct(product);
        if(createdProduct == null) return null;
        ProductDto response = getProductDto(createdProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable Long id) {
        if(id<=0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = from(productDto);
        Product updatedProduct = productService.updateProduct(product,id);
        return new ResponseEntity<>(getProductDto(updatedProduct), HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        return "Product deleted having id: "+productId;
    }

    //This exception handler listen response of all APIs present in this class
    //if we got any of mentioned exception then this method return exception message with HTTP status
    //If we want handle all the mentioned exception for any class, for that we have defined global exception handler
    /* @ExceptionHandler({IllegalArgumentException.class,NullPointerException.class})
    public ResponseEntity<String> handleExceptions(Exception ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    } */

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setDescription(product.getDescription());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product from(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
