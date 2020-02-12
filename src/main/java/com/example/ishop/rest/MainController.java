package com.example.ishop.rest;

import com.example.ishop.domain.ProductEntity;
import com.example.ishop.domain.UserEntity;
import com.example.ishop.service.CartService;
import com.example.ishop.service.ProductService;
import com.example.ishop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public MainController(UserService userService, ProductService productService, CartService cartService) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal UserEntity user, @RequestParam(required = false) Integer page,
                       @RequestParam(required = false) String sort, @RequestParam(required = false) String productName,
                       Map<String, Object> model) {
        if (sort != null && !sort.isEmpty()) {
            user.setSortParam(sort);
            user = userService.saveUser(user);
        }

        if (page == null) {
            user.setSearch(productName);
            user = userService.saveUser(user);
        }

        List<ProductEntity> products = productService.findAllProducts(page, user.getSortParam(), user.getSearch());
        List<Map<String, String>> productsInCart = cartService.findAllUserProductsByUser(user);
        Integer maxPage = productService.getMaxPage(user.getSearch());
        model.put("products", products);
        model.put("productsInCart", productsInCart);
        model.put("sizeCart", productsInCart.size());
        model.put("currentPage", page);
        model.put("maxPage", maxPage);
        model.put("currentSort", user.getSortParam());
        return "main";
    }

    @Transactional
    @RequestMapping("/buy")
    public String buy(@AuthenticationPrincipal UserEntity user) {
        cartService.buyProducts(user);
        return "redirect:/main";
    }

    @RequestMapping("/addToCart/{id}")
    public String addToCart(@AuthenticationPrincipal UserEntity user, @PathVariable("id") Long productId) {
        productService.addToCart(user, productId);
        return "redirect:/main";
    }

    @Transactional
    @RequestMapping("/removeFromCart/{id}")
    public String removeFromCart(@AuthenticationPrincipal UserEntity user, @PathVariable("id") Long productId) {
        productService.removeFromCart(user, productId);
        return "redirect:/main";
    }

}
