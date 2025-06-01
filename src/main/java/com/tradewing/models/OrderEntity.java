package com.tradewing.models;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "steps","product"})
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true, nullable = false)
    @JsonManagedReference
    private ProductEntity product;

    @Column(nullable = false)
    private String shippingAddress;

    @ElementCollection
    @CollectionTable(name = "order_steps", joinColumns = @JoinColumn(name = "order_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<OrderStep> steps = new ArrayList<>();
}
