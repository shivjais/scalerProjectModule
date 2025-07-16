package com.example.projectcatalogservice.tableInheritance.singleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_ta")
@DiscriminatorValue("1") // can be any unique value
public class Ta extends User {
    private Double ratings;
}
