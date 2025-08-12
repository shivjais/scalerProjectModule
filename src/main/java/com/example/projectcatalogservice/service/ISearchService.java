package com.example.projectcatalogservice.service;

import com.example.projectcatalogservice.dtos.SortParam;
import com.example.projectcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {
    Page<List<Product>> search(String searchString, Integer pageNumber, Integer pageSize,List<SortParam> sortParams);
}
