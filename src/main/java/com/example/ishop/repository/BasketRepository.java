package com.example.ishop.repository;

import com.example.ishop.domain.BasketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<BasketEntity, Long> {

    List<BasketEntity> findAllByUserId(Long userId);
}
