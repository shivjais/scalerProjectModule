package com.example.projectcatalogservice.tableInheritance.singleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_instructor")
@DiscriminatorValue("3") // can be any unique value
public class Instructor extends User {
    private String company;
}
