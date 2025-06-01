package com.tradewing.services.impl;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import com.tradewing.repos.ProductRepo;
import com.tradewing.services.JWTUtils;
import com.tradewing.services.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepo productRepo;
	private final JWTUtils jwt;

	@Override
	public List<ProductEntity> getProductsByName(String name){
		return productRepo.findByNameContainingIgnoreCase(name);
	}

	public void addProduct(AddProductRequest product, String token){
		UserEntity seller = jwt.decode(token);
		ProductEntity p = new ProductEntity();
		p.setName(product.getName());
		
		Long price;
		try{
			price = Long.parseLong(product.getPrice());
		}catch(NumberFormatException e){
			throw new RuntimeException("[ADDPRODUCT SERVICE] Price not Long format");
		}
		p.setPrice(price);
		p.setImage(product.getImage());
		p.setDescription(product.getDescription());
		p.setSeller(seller);
		System.out.println("[ADDPRODUCT SERVICE]: Product successfully added from profile");
		productRepo.save(p);
	}

	public void removeProduct(Long product, String token){
		jwt.decode(token);
		
		Optional<ProductEntity> rproduct = productRepo.findById(product);

		productRepo.delete(rproduct.get());
		System.out.println("[REMOVEPRODUCT SERVICE]: Product: " + rproduct.get().getName() + " removed successfully.");
	}

	@Override
	public Optional<ProductEntity> getProductsById(Long id){
		return productRepo.findById(id);
	}

	@Override
	public List<ProductEntity> getAllProducts(){
		return productRepo.findAll();
	}

	@Override
	public List<ProductEntity> findBySeller(UserEntity seller){
		return productRepo.findBySeller(seller);
	}
}