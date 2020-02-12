package com.example.ishop.repository;

import com.example.ishop.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAll(Pageable pageable);

    Page<ProductEntity> findAllByProductNameContains(String productName, Pageable pageable);
    
    @Query(value = "SELECT  count(*) FROM products", nativeQuery = true)
    Integer getSizeProductsList();

    @Query(value = "SELECT COUNT(p) FROM ProductEntity p WHERE p.productName LIKE CONCAT('%', :productName,'%')")
    Integer getSizeProductsListByProductName(@Param("productName") String productName);
}
