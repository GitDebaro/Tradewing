package com.tradewing.services;

import com.tradewing.models.OrderEntity;
import java.util.List;

public interface OrderService {

	List<OrderEntity> getAllOrders(String token);

	void createOrder(Long product, String shippingAddress, String token);

	void removeOrder(Long orderId, String token);
}