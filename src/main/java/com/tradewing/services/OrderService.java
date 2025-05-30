package com.tradewing.services;

import com.tradewing.dto.AddProductRequest;
import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import java.util.List;
import java.util.Optional;

public interface OrderService {

	void createOrder(Long product, String shippingAddress, String token);
}