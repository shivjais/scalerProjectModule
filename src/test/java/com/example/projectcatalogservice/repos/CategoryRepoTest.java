package com.example.projectcatalogservice.repos;

import com.example.projectcatalogservice.dtos.ProductDto;
import com.example.projectcatalogservice.models.Category;
import com.example.projectcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;


    @Test
    @Transactional
    public void findByIdTest() {
        Category category = categoryRepo.findById(101L).get();
        String name = category.getName();
//        for(Product product : category.getProducts()){
//            System.out.println(product.getName());
//        }
    }

    @Test
    @Transactional
    public void findAllTest() {
        //In this case subquery will be executed
        List<Category> categories = categoryRepo.findAll();
        for(Category category : categories){
            for(Product product : category.getProducts()){
                System.out.println(product.getName());
            }
        }
    }

}