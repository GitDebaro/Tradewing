package com.tradewing.controllers;

import com.tradewing.dto.OrderRequest;
import com.tradewing.models.OrderEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/order")
public interface OrderController {

    @PostMapping("/create") //ResponseEntity OK
    void createOrder(@RequestBody OrderRequest order,@RequestHeader("Authorization") String authHeader);
    
    @GetMapping("/my-orders")
    List<OrderEntity> getOrders(@RequestHeader("Authorization") String authHeader);

    @DeleteMapping("/removeOrder") //ResponseEntity OK
    void removeOrder(@RequestParam("orderId") Long productId,@RequestHeader("Authorization") String authHeader);

}