package com.tradewing.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true, nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private String shippingAddress;

    @ElementCollection
    @CollectionTable(name = "order_steps", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderStep> steps = new ArrayList<>();
}
