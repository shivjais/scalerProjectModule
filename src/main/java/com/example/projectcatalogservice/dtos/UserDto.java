package com.example.projectcatalogservice.dtos;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
