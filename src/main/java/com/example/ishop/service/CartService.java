package com.example.ishop.service;

import com.example.ishop.domain.CartEntity;
import com.example.ishop.domain.ProductEntity;
import com.example.ishop.domain.UserEntity;
import com.example.ishop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    public List<CartEntity> findAllUserProducts(UserEntity user) {
        Pageable pageable = PageRequest.of(0, 10);
        return cartRepository.findAllByUserEntityOrderByIdAsc(user, pageable);
    }

    public List<Map<String, String>>  findAllUserProductsByUser(UserEntity user) {
        List<Map<String, String>> basketsList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 10);
        List<CartEntity> basketEntities = cartRepository.findAllByUserEntityOrderByIdAsc(user, pageable);
        basketEntities.forEach(basket -> {
            Map<String, String> cartMap = new HashMap<>();
            ProductEntity product = productService.findProductById(basket.getProductEntity().getId());
            cartMap.put("productId", String.valueOf(product.getId()));
            cartMap.put("productName", product.getProductName());
            cartMap.put("productDescription", product.getDescription());
            cartMap.put("productImage", product.getImage());
            cartMap.put("productQuantity", String.valueOf(basket.getQuantity()));
            cartMap.put("productPrice", String.valueOf(basket.getQuantity() * product.getPrice()));
            basketsList.add(cartMap);
        });

        return basketsList;
    }
}
