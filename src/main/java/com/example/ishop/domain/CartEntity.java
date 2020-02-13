package com.example.ishop.domain;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {
    @Id
    @SequenceGenerator(name = "jpaSequence", sequenceName = "CART_SEQUENCE", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jpaSequence")
    private Long id;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;
}
