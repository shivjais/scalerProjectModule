package com.example.projectcatalogservice.tableInheritance.mappedSuperClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "msc_instructor")
public class Instructor extends User {
    private String company;
}
