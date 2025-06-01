package com.tradewing.repos;

import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    Optional<ProductEntity> findById(Long id);

    List<ProductEntity> findBySeller(UserEntity seller);
}