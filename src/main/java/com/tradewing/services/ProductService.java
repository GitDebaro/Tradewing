package com.tradewing.services;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import java.util.List;

public interface ProductService {

	List<ProductEntity> getProductsByName(String name);

	void addProduct(AddProductRequest product,String token);

	void removeProduct(AddProductRequest product);

	List<ProductEntity> getAllProducts();

	List<ProductEntity> findBySeller(UserEntity seller);
}