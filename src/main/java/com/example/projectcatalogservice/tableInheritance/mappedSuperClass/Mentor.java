package com.example.projectcatalogservice.tableInheritance.mappedSuperClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "msc_mentor")
public class Mentor extends User {
    private Long hours;
}
