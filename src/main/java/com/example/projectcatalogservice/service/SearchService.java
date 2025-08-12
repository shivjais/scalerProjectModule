package com.example.projectcatalogservice.service;

import com.example.projectcatalogservice.dtos.SortParam;
import com.example.projectcatalogservice.dtos.SortType;
import com.example.projectcatalogservice.models.Product;
import com.example.projectcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService implements ISearchService{

    @Autowired
    private ProductRepo productRepo;


    @Override
    public Page<List<Product>> search(String searchString, Integer pageNumber, Integer pageSize, List<SortParam> sortParams) {
        //first sort by price, if price is same sort by id
        /*Sort sortById = Sort.by("id").descending();
        Sort sort = Sort.by("price").descending().and(sortById);*/

        Sort sort = null;
        if (!sortParams.isEmpty()) {
            if(sortParams.get(0).getSortType().equals(SortType.ASC)) {
                //sort = Sort.by(Sort.Direction.ASC, "name");
                sort = Sort.by(sortParams.get(0).getSortBy()).ascending();
            }
            else
                sort = Sort.by(sortParams.get(0).getSortBy()).descending();

            for(int i=1; i<sortParams.size(); i++) {
                if(sortParams.get(i).getSortType().equals(SortType.ASC)) {
                    sort = sort.and(Sort.by(sortParams.get(i).getSortBy()).ascending());
                }
                else if(sortParams.get(i).getSortType().equals(SortType.DESC)) {
                    sort = sort.and(Sort.by(sortParams.get(i).getSortBy()).descending());
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize,sort);

        return productRepo.findByName(searchString,pageable);
    }
}
