package com.tradewing.controllers.impl;

import com.tradewing.controllers.OrderController;
import com.tradewing.controllers.ProductController;
import com.tradewing.dto.AddProductRequest;
import com.tradewing.dto.OrderRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.services.OrderService;
import com.tradewing.services.ProductService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderControllerImp implements OrderController {

    private final OrderService ordersrvc;

	@Override
	@PostMapping("/create")
	public void createOrder(@RequestBody OrderRequest order){
        ordersrvc.createOrder(order.productId, order.buyerId, order.shippingAddress);
        System.out.println("[ORDERSERVICE]: Order created");
    }

}