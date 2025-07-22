package com.example.projectcatalogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    private String name;
    private String description;

    //due to mappedBy JPA will not reconsider this mapping because it's already defined in product class
    //JPA will not create a separate table
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    //@Fetch(FetchMode.SELECT)
    //@BatchSize(size=2)
    private List<Product> products;

    public Category() {
        this.products = new ArrayList<>();
    }
}
