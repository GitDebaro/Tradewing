package com.tradewing.controllers;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RequestMapping("/api/products")
public interface ProductController {

    @GetMapping("/search")
    List<ProductEntity> getProductsByName(@RequestParam String name);

    @GetMapping("/searchId")
    ResponseEntity<ProductEntity> getProductsById(@RequestParam Long id);	

    @PostMapping("/addProduct") //ResponseEntity OK
    void addProduct(@RequestBody AddProductRequest product, @RequestHeader("Authorization") String authHeader);

    @DeleteMapping("/removeProduct") //ResponseEntity OK
    void removeProduct(@RequestParam("productId") Long productId, @RequestHeader("Authorization") String authHeader);

    @GetMapping("")
    List<ProductEntity> getAllProducts();	
    

}