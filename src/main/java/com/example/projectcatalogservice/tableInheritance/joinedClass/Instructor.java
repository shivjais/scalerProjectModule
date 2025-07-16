package com.example.projectcatalogservice.tableInheritance.joinedClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_instructor")
@PrimaryKeyJoinColumn(name="user_id") //this is used to provide the name of FK
public class Instructor extends User {
    private String company;
}
