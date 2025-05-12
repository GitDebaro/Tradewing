package com.tradewing.services.impl;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import com.tradewing.repos.ProductRepo;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepo productRepo;
	private final UserRepo usrp;

	@Override
	public List<ProductEntity> getProductsByName(String name){
		return productRepo.findByNameContainingIgnoreCase(name);
	}

	public void addProduct(AddProductRequest product){
		UserEntity seller;
		try{
			seller = usrp.findUserByEmail(product.getVendedor());
		}catch(Exception e){
			throw new RuntimeException("[ADDPRODUCT]: User not found");
		}
		ProductEntity p = new ProductEntity();
		p.setName(product.getName());
		
		Long price;
		try{
			price = Long.parseLong(product.getPrice());
		}catch(NumberFormatException e){
			throw new RuntimeException("Price not Long format");
		}
		p.setPrice(price);
		p.setImage(product.getImage());
		p.setDescription(product.getDescription());
		p.setVendedor(seller);
		System.out.println("[ADDPRODUCT]: Product successfully added from profile");
		productRepo.save(p);
	}

	public void removeProduct(AddProductRequest product){
		System.out.println("RemoveProduct");
	}

	@Override
	public List<ProductEntity> getAllProducts(){
		return productRepo.findAll();
	}


}