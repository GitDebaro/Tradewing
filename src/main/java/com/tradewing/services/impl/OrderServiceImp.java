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

    @Value("${my.secret.jwt}")
    private String jwtSecret;
    
    private final ProductRepo productRepo;
	private final UserRepo usrp;
    private final OrderRepo orderRepo;

    @Override
    public List<OrderEntity> getAllOrders(String token){
        UserEntity user = getUserFromToken(token);
        return orderRepo.findAllByUserEmail(user.getEmail());
    }


    @Override
    public void removeOrder(Long orderId, String token) {
        UserEntity user = getUserFromToken(token);
        Optional<OrderEntity> orderOpt = orderRepo.findById(orderId);

        if (orderOpt.isEmpty()) {
            throw new RuntimeException("Pedido no encontrado");
        }

        OrderEntity order = orderOpt.get();

        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para eliminar este pedido");
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
        UserEntity user = getUserFromToken(token);

        OrderEntity order = new OrderEntity();
        LocalDateTime start = LocalDateTime.now();
        List<OrderStep> steps = generateSteps(start);

        Optional <ProductEntity> orderProd;
        orderProd = productRepo.findById(product);

        order.setProduct(orderProd.get());

        order.setUser(user);

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
        LocalDateTime pedidoRecibido = startTime.plusMinutes(1/*+ new Random().nextInt(0)*/);
        steps.add(new OrderStep("Pedido recibido", pedidoRecibido));

        // Paso 2: Saliendo del warehouse (30 min a 1h después del anterior)
        LocalDateTime saliendo = pedidoRecibido.plusMinutes(1);
        steps.add(new OrderStep("Saliendo del warehouse", saliendo));

        // Paso 3: En reparto (30 min a 1h después del anterior)
        LocalDateTime enReparto = saliendo.plusMinutes(1);
        steps.add(new OrderStep("En reparto", enReparto));

        // Paso 4: Recibido (15-30 min después del anterior)
        LocalDateTime entregado = enReparto.plusMinutes(1);
        steps.add(new OrderStep("Entregado", entregado));

        return steps;
    }

    private SecretKey getSigningKey() {
    	return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
    private UserEntity getUserFromToken (String token){
        UserEntity user;
		Claims claims = Jwts.parserBuilder()
						.setSigningKey(getSigningKey())
						.build()
						.parseClaimsJws(token)
						.getBody();
		String email = claims.getIssuer();
		Date expiration = claims.getExpiration();
		Date now = new Date();
		if(expiration.before(now)){
			throw new RuntimeException("[CREATEORDER SERVICE]: Token is expired");
		}
		try{
			user = usrp.findUserByEmail(email);
		}catch(Exception e){
			throw new RuntimeException("[CREATEORDER SERVICE]: User not found");
		}

        return user;
    }
}