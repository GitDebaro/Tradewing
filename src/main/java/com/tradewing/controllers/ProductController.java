package com.tradewing.controllers;

import com.tradewing.models.ProductEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequestMapping("/api/products")
public interface ProductController {

    @GetMapping("/search")
    List<ProductEntity> getProductsByName(@RequestParam String name);

    @GetMapping("/searchId")
    ResponseEntity<ProductEntity> getProductsById(@RequestParam Long id);	

    @PostMapping("/addProduct")
    void addProduct(@RequestBody ProductEntity product);

    @GetMapping("")
    List<ProductEntity> getAllProducts();	
    

}