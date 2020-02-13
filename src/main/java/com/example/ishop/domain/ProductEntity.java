package com.example.ishop.domain;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @SequenceGenerator(name = "jpaSequence", sequenceName = "PRODUCT_SEQUENCE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
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

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productEntity", orphanRemoval=true)
    private Set<CartEntity> cartEntities;
}
