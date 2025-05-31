package com.tradewing.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price; 

    @ManyToOne
    @JoinColumn(name = "seller")
    @JsonIgnoreProperties({"products", "password"})
    private UserEntity seller;

    @Column(nullable = false, length = 500)
    private String description;

    private String image;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference // evita bucles infinitos en la serialización
    private OrderEntity order;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}