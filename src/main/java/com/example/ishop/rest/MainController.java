package com.example.ishop.rest;

import com.example.ishop.domain.ProductEntity;
import com.example.ishop.domain.Role;
import com.example.ishop.domain.UserEntity;
import com.example.ishop.service.CartService;
import com.example.ishop.service.ProductService;
import com.example.ishop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "redirect:/main";
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
        Integer maxPage = productService.getMaxPage(user.getSearch());
        model.put("products", products);
        model.put("currentPage", page);
        model.put("maxPage", maxPage);
        model.put("currentSort", user.getSortParam());
        model.put("username", user.getUsername());

        if (user.getRoles().contains(Role.ADMIN)) {
            model.put("isAdmin", true);
        } else {
            List<Map<String, String>> productsInCart = cartService.findAllUserProductsByUser(user);
            model.put("productsInCart", productsInCart);
            model.put("sizeCart", productsInCart.size());
        }
        return "main";
    }

    @Transactional
    @RequestMapping("/buy")
    public String buy(@AuthenticationPrincipal UserEntity user) {
        cartService.buyProducts(user);
        return "redirect:/main";
    }

    @RequestMapping("/removeProduct/{id}")
    public String removeProduct(@AuthenticationPrincipal UserEntity user, @PathVariable("id") Long productId) {
        productService.removeProduct(productId);
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

    @RequestMapping("/update")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public String updateProduct(@RequestParam Long id,
                                @RequestParam String productName,
                                @RequestParam String description,
                                @RequestParam String image,
                                @RequestParam Double price,
                                @RequestParam Integer quantity) {
        return productService.updateProduct(id, productName, description, image, price, quantity);
    }

    @RequestMapping("/add")
    public String updateProduct(@RequestParam String productName,
                                @RequestParam String description,
                                @RequestParam String image,
                                @RequestParam Double price,
                                @RequestParam Integer quantity) {
        return productService.addNewProduct(productName, description, image, price, quantity);
    }
}
