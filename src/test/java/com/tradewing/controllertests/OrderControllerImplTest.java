package com.tradewing.controllertests;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.tradewing.models.UserEntity;
import com.tradewing.models.OrderEntity;
import com.tradewing.models.OrderStep;
import com.tradewing.models.ProductEntity;
import com.tradewing.services.ProductService;
import com.tradewing.services.UserService;
import com.tradewing.services.OrderService;
import com.tradewing.controllers.impl.OrderControllerImp;
import com.tradewing.controllers.impl.ProductControllerImpl;
import com.tradewing.controllers.impl.UserControllerImpl;
import com.tradewing.dto.AddProductRequest;
import com.tradewing.dto.OrderRequest;
import com.tradewing.dto.TokenCredential;
import com.tradewing.dto.UpdateUserPayload;
import com.tradewing.dto.UserInfo;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderControllerImplTest {
    
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderControllerImp orderController;

    private ProductEntity product1;
    private UserEntity user1;
    private OrderEntity order1;
    private List<OrderStep> steps1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("Product Test 2");
        product1.setDescription("Test");
        product1.setImage("https://placehold.co/400x400?text=Producto2");

        user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("Test User");
        user1.setEmail("test@user.com");
        user1.setPassword("sha256");

        OrderStep step = new OrderStep("Recibido", java.time.LocalDateTime.now());
        steps1 = List.of(step);

        order1 = new OrderEntity();
        order1.setId(1L);
        order1.setProduct(product1);
        order1.setShippingAddress("mockAddress");
        order1.setUser(user1);
        order1.setSteps(steps1);

    }

    @Test
    void testCreateOrder() {
        String authHeader = "Bearer faketoken";
        String token = authHeader.replace("Bearer ","");
        OrderRequest request = new OrderRequest();
        request.productId = 1L;
        request.shippingAddress = "Fake address";

        orderController.createOrder(request, token);

        verify(orderService, times(1)).createOrder(1L, "Fake address", "faketoken");
    }

    @Test
    void testGetOrders() {
        String token = "mocktoken";

        when(orderService.getAllOrders(token)).thenReturn(List.of(order1));

        List<OrderEntity> result = orderController.getOrders(token);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order1.getId(), result.get(0).getId());
        verify(orderService, times(1)).getAllOrders(token);
    }

    @Test
    void testRemoveOrder() {
        String authHeader = "Bearer faketoken";
        String token = authHeader.replace("Bearer ","");
        Long orderId = 1L;

        orderController.removeOrder(orderId, token);

        verify(orderService, times(1)).removeOrder(orderId, "faketoken");
    }
        
}


