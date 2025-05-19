package com.tradewing.services;

import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import java.util.List;
import java.util.Optional;

public interface ProductService {

	List<ProductEntity> getProductsByName(String name);

	Optional<ProductEntity> getProductsById(Long id);

	void addProduct(ProductEntity product);

	List<ProductEntity> getAllProducts();

	List<ProductEntity> findBySeller(UserEntity seller);
}