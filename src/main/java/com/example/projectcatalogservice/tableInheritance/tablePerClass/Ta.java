package com.example.projectcatalogservice.tableInheritance.tablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_ta")
public class Ta extends User {
    private Double ratings;
}
