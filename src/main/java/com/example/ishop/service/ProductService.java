package com.example.ishop.service;

import com.example.ishop.domain.CartEntity;
import com.example.ishop.domain.ProductEntity;
import com.example.ishop.domain.SortParam;
import com.example.ishop.domain.UserEntity;
import com.example.ishop.repository.CartRepository;
import com.example.ishop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final static Integer PAGE_SIZE = 10;
    private final static Integer DEFAULT_PAGE = 0;
    private final static String DEFAULT_SORT = "productName";

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public List<ProductEntity> findAllProducts(Integer currentPage, String currentSort, String productName) {
        int page = currentPage == null ? DEFAULT_PAGE : currentPage - 1;
        Sort sort;
        if (currentSort == null) {
            sort = Sort.by(DEFAULT_SORT).ascending();
        } else {
            switch (currentSort) {
                case SortParam.NAME_ASC:
                    sort = Sort.by("productName").ascending().and(Sort.by("productName"));
                    break;
                case SortParam.NAME_DESC:
                    sort = Sort.by("productName").descending().and(Sort.by("productName"));
                    break;
                case SortParam.PRICE_ASC:
                    sort = Sort.by("price").ascending().and(Sort.by("productName"));
                    break;
                case SortParam.PRICE_DESC:
                    sort = Sort.by("price").descending().and(Sort.by("productName"));
                    break;
                default:
                    sort = Sort.by(DEFAULT_SORT).ascending().and(Sort.by("productName"));
            }
        }
        if (productName != null && !productName.isEmpty()) {
            return productRepository.findAllByProductNameContains(productName, PageRequest.of(page, PAGE_SIZE, sort)).getContent();
        }
        return productRepository.findAll(PageRequest.of(page, PAGE_SIZE, sort)).getContent();
    }

    public Integer getMaxPage(String productName) {

        int countOfPage;
        if (productName == null || productName.isEmpty()) {
            countOfPage = productRepository.getSizeProductsList() / PAGE_SIZE;
        } else {
            countOfPage = productRepository.getSizeProductsListByProductName(productName) / PAGE_SIZE;
        }

        if (productRepository.getSizeProductsList() % PAGE_SIZE == 0) {
            return countOfPage;
        } else {
            return countOfPage + 1;
        }
    }

    public ProductEntity findProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public void addToCart(UserEntity user, Long productId) {
        productRepository.findById(productId).ifPresent(p -> {
            if (p.getQuantity() > 0) {
                p.setQuantity(p.getQuantity() - 1);

                productRepository.save(p);

                CartEntity cart = cartRepository.findByUserEntityAndProductEntity(user, p)
                        .orElseGet(() -> new CartEntity(null, p, user, 0, 0.0));

                cart.setQuantity(cart.getQuantity() + 1);
                cart.setPrice(cart.getQuantity() * p.getPrice());
                cartRepository.save(cart);
            }
        });
    }

    public void removeFromCart(UserEntity user, Long productId) {
        productRepository.findById(productId).ifPresent(product -> {
            cartRepository.findByUserEntityAndProductEntity(user, product).ifPresent(cart -> {
                if (cart.getQuantity() > 1) {
                    cart.setQuantity(cart.getQuantity() - 1);
                    cartRepository.save(cart);
                } else {
                    cartRepository.deleteByProductEntityAndUserEntity(product, user);
                }
                product.setQuantity(product.getQuantity() + 1);
                productRepository.save(product);
            });
        });
    }

}
