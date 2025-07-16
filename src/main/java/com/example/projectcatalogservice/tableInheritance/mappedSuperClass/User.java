package com.example.projectcatalogservice.tableInheritance.mappedSuperClass;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * This is not an approach of approach, but we can use it when
 * we don't want to create the table of parent class & put all column
 * of parent table in child tables
 * For that we have make parent class abstract
 * */

@MappedSuperclass
public abstract class User {
    @Id
    private UUID id;
    private String email;
}
