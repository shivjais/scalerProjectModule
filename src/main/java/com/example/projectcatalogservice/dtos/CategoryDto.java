package com.example.projectcatalogservice.dtos;

import com.example.projectcatalogservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
