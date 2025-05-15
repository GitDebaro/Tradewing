package com.tradewing.services.impl;

import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import com.tradewing.repos.ProductRepo;
import com.tradewing.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepo productRepo;

	@Override
	public List<ProductEntity> getProductsByName(String name){
		return productRepo.findByNameContainingIgnoreCase(name);
	}

	@Override
	public Optional<ProductEntity> getProductsById(Long id){
		return productRepo.findById(id);
	}


	@Override
	public void addProduct(ProductEntity product){
		productRepo.save(product);
	}

	@Override
	public List<ProductEntity> getAllProducts(){
		return productRepo.findAll();
	}

	@Override
	public List<ProductEntity> findBySeller(UserEntity seller){
		return productRepo.findByVendedor(seller);
	}
}