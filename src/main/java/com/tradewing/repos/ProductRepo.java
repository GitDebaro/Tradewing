package com.tradewing.repos;

import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    List<ProductEntity> findByVendedor(UserEntity vendedor);
}