package com.example.projectcatalogservice.tableInheritance.joinedClass;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "jc_user")
//this will create this table & add FK in its child table
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    private UUID id;
    private String email;
}
