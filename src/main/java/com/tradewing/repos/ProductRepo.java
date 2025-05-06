package com.tradewing.repos;

import com.tradewing.models.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByName(String name);
}