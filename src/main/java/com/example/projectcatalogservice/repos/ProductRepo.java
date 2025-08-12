package com.example.projectcatalogservice.repos;

import com.example.projectcatalogservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long id);

    Product save(Product product);

    List<Product> findProductByPriceBetween(Double priceAfter, Double priceBefore);

    List<Product> findAllByOrderByPriceDesc();

    //@Query("select p.description from Product p where p.id=?1") //Passing parameter value by position
    @Query("select p.description from Product p where p.id=:productId") //Passing parameter value by name
    String getDescriptionByProductId(Long productId);

    Page<List<Product>> findByName(String query, Pageable pageable);

}
