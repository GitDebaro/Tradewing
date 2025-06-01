package com.tradewing.services.impl;

import com.tradewing.models.UserEntity;
import com.tradewing.models.OrderEntity;
import com.tradewing.models.OrderStep;
import com.tradewing.models.ProductEntity;
import com.tradewing.repos.OrderRepo;
import com.tradewing.repos.ProductRepo;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.OrderService;
import com.tradewing.services.JWTUtils;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    
    private final ProductRepo productRepo;
	private final UserRepo usrp;
    private final OrderRepo orderRepo;
    private final JWTUtils jwt;

    @Override
    public List<OrderEntity> getAllOrders(String token){
        UserEntity user = jwt.decode(token);
        return orderRepo.findAllByUserEmail(user.getEmail());
    }


    @Override
    public void removeOrder(Long orderId, String token) {
        UserEntity user = jwt.decode(token);
        Optional<OrderEntity> orderOpt = orderRepo.findById(orderId);

        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        OrderEntity order = orderOpt.get();

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Operation denied, Unauthorized");
        }

        ProductEntity product = order.getProduct();
        if (product != null) {
            product.setOrder(null);
            productRepo.save(product); // guarda el cambio
        }
        if (user != null) {
            user.getOrders().remove(order);
            usrp.save(user); // guarda el cambio
        }

        orderRepo.delete(order);
        System.out.println("[ORDERSERVICE]: Order deleted: " + order.getProduct().getName());
    }



    @Override
    public void createOrder(Long product, String shippingAddress, String token) {
        UserEntity user = jwt.decode(token);

        OrderEntity order = new OrderEntity();
        LocalDateTime start = LocalDateTime.now();
        List<OrderStep> steps = generateSteps(start);

        ProductEntity prod = productRepo.findById(product).get();

        // Verificar disponibilidad
        if (!Boolean.TRUE.equals(prod.getAvailable())) {
            throw new RuntimeException("Product not available: " + prod.getName());
        }

        prod.setAvailable(false);
        productRepo.save(prod);

        order.setProduct(prod);

        order.setUser(user);

        order.setShippingAddress(shippingAddress);

        order.setSteps(steps);

        System.out.println("[CREATEORDER SERVICE]: Check steps: "+ order.getSteps());
		System.out.println("[CREATEORDER SERVICE]: Check product available: "+ order.getProduct().getAvailable());
		System.out.println("[CREATEORDER SERVICE]: Check user: "+ order.getUser().getEmail());
		System.out.println("[CREATEORDER SERVICE]: Order successfully created");
		orderRepo.save(order);
    }

    public List<OrderStep> generateSteps(LocalDateTime startTime) {
        List<OrderStep> steps = new ArrayList<>();

        // Step 1: 2/3h
        LocalDateTime received = startTime.plusMinutes(1/*+ new Random().nextInt(0)*/);
        steps.add(new OrderStep("Order received", received));

        // Step 2: 30 min 1h
        LocalDateTime exiting = received.plusMinutes(1);
        steps.add(new OrderStep("Leaving the warehouse", exiting));

        // Step 3: 30 min 1h
        LocalDateTime distrb = exiting.plusMinutes(1);
        steps.add(new OrderStep("In distribution", distrb));

        // Step 4: 15-30 min 
        LocalDateTime delivered = distrb.plusMinutes(1);
        steps.add(new OrderStep("Delivered", delivered));

        return steps;
    }

}