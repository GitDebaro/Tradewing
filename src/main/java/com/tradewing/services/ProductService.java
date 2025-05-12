package com.tradewing.services;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import java.util.List;

public interface ProductService {

	List<ProductEntity> getProductsByName(String name);

	void addProduct(AddProductRequest product);

	void removeProduct(AddProductRequest product);

	List<ProductEntity> getAllProducts();


}