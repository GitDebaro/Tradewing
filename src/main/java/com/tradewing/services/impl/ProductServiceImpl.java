package com.tradewing.services.impl;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import com.tradewing.repos.ProductRepo;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.ProductService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepo productRepo;
	private final UserRepo usrp;

	@Value("${my.secret.jwt}")
    private String jwtSecret;

	@Override
	public List<ProductEntity> getProductsByName(String name){
		return productRepo.findByNameContainingIgnoreCase(name);
	}

	public void addProduct(AddProductRequest product, String token){
		UserEntity seller;
		Claims claims = Jwts.parserBuilder()
						.setSigningKey(getSigningKey())
						.build()
						.parseClaimsJws(token)
						.getBody();
		String email = claims.getIssuer();
		Date expiration = claims.getExpiration();
		Date now = new Date();
		if(expiration.before(now)){
			throw new RuntimeException("[ADDPRODUCT SERVICE]: Token is expired");
		}
		try{
			seller = usrp.findUserByEmail(email);
		}catch(Exception e){
			throw new RuntimeException("[ADDPRODUCT SERVICE]: User not found");
		}
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
		p.setVendedor(seller);
		System.out.println("[ADDPRODUCT SERVICE]: Check vendedor: "+ seller);
		System.out.println("[ADDPRODUCT SERVICE]: Check vendedor en el producto: "+ p.getVendedor());
		System.out.println("[ADDPRODUCT SERVICE]: Check vendedor en el producto: "+ p.getVendedor().getEmail());
		System.out.println("[ADDPRODUCT SERVICE]: Check producto: "+ p);
		System.out.println("[ADDPRODUCT SERVICE]: Product successfully added from profile");
		productRepo.save(p);
	}

	public void removeProduct(AddProductRequest product){
		System.out.println("RemoveProduct");
	}

	@Override
	public List<ProductEntity> getAllProducts(){
		return productRepo.findAll();
	}

	private SecretKey getSigningKey() {
    	return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
}