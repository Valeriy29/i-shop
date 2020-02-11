package com.example.ishop.repository;

import com.example.ishop.domain.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByOrderByProductNameAsc(Pageable pageable);
    
    @Query(value = "SELECT  count(*) FROM products", nativeQuery = true)
    Integer getSizeProductsList();
}
