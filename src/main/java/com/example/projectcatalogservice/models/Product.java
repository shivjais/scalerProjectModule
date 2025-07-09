package com.example.projectcatalogservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {
    private String name;
    private String description;

    //due to cascadeType ALL, all operation on parent will be reflected to child
    //if we create a product whose category doesn't exist, then it will create category as well
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    private Double price;
    private String imageUrl;
    private Boolean isPrimeSpecific; //Business specific field which we don't want to expose to buyer or seller

    public Product(){
    }
}
