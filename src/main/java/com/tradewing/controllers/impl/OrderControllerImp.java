package com.tradewing.controllers.impl;

import com.tradewing.controllers.OrderController;
import com.tradewing.controllers.ProductController;
import com.tradewing.dto.AddProductRequest;
import com.tradewing.dto.OrderRequest;
import com.tradewing.models.OrderEntity;
import com.tradewing.models.ProductEntity;
import com.tradewing.services.OrderService;
import com.tradewing.services.ProductService;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderControllerImp implements OrderController {

    private final OrderService ordersrvc;

	@Override
	@PostMapping("/create")
	public void createOrder(@RequestBody OrderRequest order,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer","");
        ordersrvc.createOrder(order.productId, order.shippingAddress,token);
        System.out.println("[ORDERCONTROLLER]: Order created");
    }

    @Override
    @GetMapping("/my-orders")
    public List<OrderEntity> getOrders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer","");
        List <OrderEntity> orders = ordersrvc.getAllOrders(token);
        System.out.println("[ORDERCONTROLLER]: My orders: \n");
        System.out.println(orders);
        return orders;
    }

    @Override
    @DeleteMapping("/removeOrder")
    public void removeOrder(@RequestParam("orderId") Long productId,@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer","");
        ordersrvc.removeOrder(productId, token);
        System.out.println("[ORDERCONTROLLER]: Order removed successfully");
    }

}