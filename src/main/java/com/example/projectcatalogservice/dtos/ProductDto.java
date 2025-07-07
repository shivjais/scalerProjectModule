package com.example.projectcatalogservice.dtos;

import com.example.projectcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Category category;
    private Double price;
    private String imageUrl;

}
