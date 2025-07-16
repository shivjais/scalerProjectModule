package com.example.projectcatalogservice.tableInheritance.singleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "st_mentor")
@DiscriminatorValue("2") // can be any unique value
public class Mentor extends User {
    private Long hours;
}
