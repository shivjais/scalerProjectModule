package com.example.projectcatalogservice.tableInheritance.singleTable;

import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "st_user")
//This will create single table for all type of user
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//This a key which is used to distinguish diff type of user
//here key is user_type & we have to define its value for all user type in their POJO
@DiscriminatorColumn(name="user_type",discriminatorType = DiscriminatorType.INTEGER)
public class User {
    @Id
    private UUID id;
    private String email;
}
