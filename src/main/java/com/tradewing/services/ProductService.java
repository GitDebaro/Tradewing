package com.tradewing.services;

import com.tradewing.models.ProductEntity;
import java.util.List;

public interface ProductService {

	List<ProductEntity> getProductsByName(String name);

	void addProduct(ProductEntity product);

}