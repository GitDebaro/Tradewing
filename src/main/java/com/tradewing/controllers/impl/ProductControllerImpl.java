package com.tradewing.controllers.impl;

import com.tradewing.controllers.ProductController;
import com.tradewing.models.ProductEntity;
import com.tradewing.services.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;



import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductControllerImpl implements ProductController {

	private final ProductService productSC;

	@Override
	@GetMapping("/search")
	public List<ProductEntity> getProductsByName(@RequestParam String name){
		return productSC.getProductsByName(name);
	}

	@Override
	@GetMapping("/searchId")
    public ResponseEntity<ProductEntity> getProductsById(@RequestParam Long id){
		return productSC.getProductsById(id)
		.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
	}

	@Override
	@PostMapping("/addProduct")
	public void addProduct(@RequestBody ProductEntity product){
		productSC.addProduct(product);
	}

	@Override
	@GetMapping("")
    public List<ProductEntity> getAllProducts(){
		return productSC.getAllProducts();
	}

}