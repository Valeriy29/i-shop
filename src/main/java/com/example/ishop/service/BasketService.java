package com.example.ishop.service;

import com.example.ishop.domain.BasketEntity;
import com.example.ishop.domain.ProductEntity;
import com.example.ishop.domain.UserEntity;
import com.example.ishop.repository.BasketRepository;
import com.example.ishop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAllUserProducts(UserEntity user) {
        List<BasketEntity> baskets = basketRepository.findAllByUserId(user.getId());
        List<ProductEntity> products = new ArrayList<>();
        baskets.forEach(basket -> {
            productRepository.findById(basket.getProductId()).ifPresent(product -> {
                product.setQuantity(basket.getQuantity());
                product.setPrice(product.getPrice() * basket.getQuantity());
                products.add(product);
            });
        });
        return products;
    }
}
