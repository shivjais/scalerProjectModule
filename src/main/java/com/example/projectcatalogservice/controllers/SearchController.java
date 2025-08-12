package com.example.projectcatalogservice.controllers;

import com.example.projectcatalogservice.dtos.SearchRequestDto;
import com.example.projectcatalogservice.models.Product;
import com.example.projectcatalogservice.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @PostMapping
    public ResponseEntity<Page<List<Product>>> search(@RequestBody SearchRequestDto searchRequestDto){
        Page<List<Product>> products = searchService.search(searchRequestDto.getQuery(),searchRequestDto.getPageNumber(),searchRequestDto.getPageSize(),searchRequestDto.getSortParams());
        return ResponseEntity.ok().body(products);
    }

}
