package com.tradewing.services.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.tradewing.models.UserEntity;
import com.tradewing.models.OrderEntity;
import com.tradewing.models.OrderStep;
import com.tradewing.models.ProductEntity;
import com.tradewing.repos.OrderRepo;
import com.tradewing.repos.ProductRepo;
import com.tradewing.repos.UserRepo;
import com.tradewing.services.UserService;
import com.tradewing.services.OrderService;
import com.tradewing.services.ProductService;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import lombok.RequiredArgsConstructor;
import com.tradewing.dto.UserInfo;
import com.tradewing.dto.UpdateUserPayload;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Value;


import javax.crypto.SecretKey;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    
    private final ProductRepo productRepo;
	private final UserRepo usrp;
    private final OrderRepo orderRepo;

    @Override
    public void createOrder(Long product, Long user, String shippingAddress) {
        OrderEntity order = new OrderEntity();
        LocalDateTime start = LocalDateTime.now();
        List<OrderStep> steps = generateSteps(start);

        Optional <ProductEntity> orderProd;
        orderProd = productRepo.findById(product);

        order.setProduct(orderProd.get());

        Optional <UserEntity> orderUser;
        orderUser = usrp.findById(user);

        order.setUser(orderUser.get());

        order.setShippingAddress(shippingAddress);

        order.setSteps(steps);

        System.out.println("[CREATEORDER SERVICE]: Check steps: "+ order.getSteps());
		System.out.println("[CREATEORDER SERVICE]: Check producto: "+ order.getProduct().getName());
		System.out.println("[CREATEORDER SERVICE]: Check user: "+ order.getUser().getEmail());
		System.out.println("[CREATEORDER SERVICE]: Order successfully created");
		orderRepo.save(order);
    }

    public List<OrderStep> generateSteps(LocalDateTime startTime) {
        List<OrderStep> steps = new ArrayList<>();

        // Paso 1: Pedido recibido (entre 2 y 3 horas desde ahora)
        LocalDateTime pedidoRecibido = startTime.plusMinutes(120 + new Random().nextInt(60));
        steps.add(new OrderStep("Pedido recibido", pedidoRecibido));

        // Paso 2: Saliendo del warehouse (30 min a 1h después del anterior)
        LocalDateTime saliendo = pedidoRecibido.plusMinutes(30 + new Random().nextInt(30));
        steps.add(new OrderStep("Saliendo del warehouse", saliendo));

        // Paso 3: En reparto (30 min a 1h después del anterior)
        LocalDateTime enReparto = saliendo.plusMinutes(30 + new Random().nextInt(30));
        steps.add(new OrderStep("En reparto", enReparto));

        // Paso 4: Recibido (15-30 min después del anterior)
        LocalDateTime recibido = enReparto.plusMinutes(15 + new Random().nextInt(15));
        steps.add(new OrderStep("Recibido", recibido));

        return steps;
    }
	
}