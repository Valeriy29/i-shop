package com.example.ishop.rest;

import com.example.ishop.domain.ProductEntity;
import com.example.ishop.domain.UserEntity;
import com.example.ishop.service.BasketService;
import com.example.ishop.service.ProductService;
import com.example.ishop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final UserService userService;
    private final ProductService productService;
    private final BasketService basketService;

    @Autowired
    public MainController(UserService userService, ProductService productService, BasketService basketService) {
        this.userService = userService;
        this.productService = productService;
        this.basketService = basketService;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal UserEntity user, Map<String, Object> model) {
        List<ProductEntity> products = productService.findAllProducts();
        List<ProductEntity> productsInBasket = basketService.findAllUserProducts(user);
        model.put("products", products);
        model.put("productsInBasket", productsInBasket);
        return "main";
    }

}
