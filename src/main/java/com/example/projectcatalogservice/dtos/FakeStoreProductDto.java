package com.example.projectcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
//we need to implement Serializable because while persisting object in redis cache/DB, redis
//deserialize the object
public class FakeStoreProductDto implements Serializable {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
