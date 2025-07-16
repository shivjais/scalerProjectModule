package com.example.projectcatalogservice.tableInheritance.mappedSuperClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "msc_ta")
public class Ta extends User {
    private Double ratings;
}
