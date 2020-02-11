package com.example.ishop.repository;

import com.example.ishop.domain.CartEntity;
import com.example.ishop.domain.ProductEntity;
import com.example.ishop.domain.UserEntity;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findAllByUserEntityOrderByIdAsc(UserEntity userEntity, Pageable pageable);

    Optional<CartEntity> findByUserEntityAndProductEntity(UserEntity userEntity, ProductEntity productEntity);

    void deleteByProductEntityAndUserEntity(ProductEntity productEntity, UserEntity userEntity);
}
