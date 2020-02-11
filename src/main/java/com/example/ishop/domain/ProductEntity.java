package com.example.ishop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_name", length = 1024)
    private String productName;

    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productEntity", orphanRemoval=true)
    private Set<CartEntity> cartEntities;
}
