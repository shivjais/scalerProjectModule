package com.example.projectcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SortParam {
    private String sortBy;
    private SortType sortType;
}
