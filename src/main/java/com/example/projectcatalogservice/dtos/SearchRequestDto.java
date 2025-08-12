package com.example.projectcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String query;
    private Integer pageNumber;
    private Integer pageSize;
    private List<SortParam> sortParams;
}
