package com.example.projectcatalogservice.controllers;

import com.example.projectcatalogservice.dtos.ProductDto;
import com.example.projectcatalogservice.models.Product;
import com.example.projectcatalogservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        //it's better to add validation in controller
        if(productId<=0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = productService.getProductById(productId);
        if(product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ProductDto productDto = getProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            productDto.setCategory(product.getCategory());
        }
        return productDto;
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return null;
    }

    @PutMapping("/product")
    public ProductDto updateProduct(@RequestBody ProductDto product) {

        return null;
    }

    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        return "Product deleted having id: "+productId;
    }
}
