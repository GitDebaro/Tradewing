package com.tradewing.models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.util.List;



@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String surname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 500)
    private String password;

    private String image = "https://definicion.de/wp-content/uploads/2019/07/perfil-de-usuario.png";

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}