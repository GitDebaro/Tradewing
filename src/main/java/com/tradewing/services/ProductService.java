package com.tradewing.services;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import java.util.List;
import java.util.Optional;

public interface ProductService {

	List<ProductEntity> getProductsByName(String name);

	void addProduct(AddProductRequest product,String token);

	void removeProduct(Long product, String token);
	
	Optional<ProductEntity> getProductsById(Long id);

	List<ProductEntity> getAllProducts();

	List<ProductEntity> findBySeller(UserEntity seller);

}