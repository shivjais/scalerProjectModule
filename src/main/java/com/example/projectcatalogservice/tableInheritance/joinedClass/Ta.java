package com.example.projectcatalogservice.tableInheritance.joinedClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_ta")
@PrimaryKeyJoinColumn(name="user_id") //this is used to provide the name of FK
public class Ta extends User {
    private Double ratings;
}
