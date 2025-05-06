package com.tradewing.services.impl;

import com.tradewing.models.ProductEntity;
import com.tradewing.repos.ProductRepo;
import com.tradewing.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepo productRepo;

	@Override
	public List<ProductEntity> getProductsByName(String name){
		return productRepo.findByName(name);
	}

	@Override
	public void addProduct(ProductEntity product){
		productRepo.save(product);
	}

}