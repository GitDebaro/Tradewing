package com.tradewing.controllers;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/products")
public interface ProductController {

    @GetMapping("/search")
    List<ProductEntity> getProductsByName(@RequestParam String name);	

    @PostMapping("/addProduct")
    void addProduct(@RequestBody AddProductRequest product, @RequestHeader("Authorization") String authHeader);

    @PostMapping("/removeProduct")
    void removeProduct(@RequestBody ProductEntity product, @RequestHeader("Authorization") String authHeader);

    @GetMapping("")
    List<ProductEntity> getAllProducts();	
    

}