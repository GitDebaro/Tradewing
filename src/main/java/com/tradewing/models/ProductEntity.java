package com.tradewing.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonBackReference;
=======
import com.tradewing.models.UserEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
>>>>>>> dev

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
    @JoinColumn(name = "vendedor")
    @JsonIgnoreProperties({"products", "password"})
    private UserEntity vendedor;

    @Column(nullable = false, length = 500)
    private String description;

    private String image;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}