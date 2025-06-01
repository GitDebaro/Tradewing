package com.tradewing.servicetests;

import com.tradewing.models.OrderEntity;
import com.tradewing.models.OrderStep;
import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import com.tradewing.services.JWTUtils;
import com.tradewing.services.impl.OrderServiceImp;
import com.tradewing.repos.OrderRepo;
import com.tradewing.repos.ProductRepo;
import com.tradewing.repos.UserRepo;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {
    @Mock
    private ProductRepo productRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private OrderServiceImp orderService;

    private UserEntity mockUser;
    private ProductEntity mockProduct;
    private OrderEntity mockOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setEmail("user@example.com");
        mockUser.setOrders(new ArrayList<>());

        mockProduct = new ProductEntity();
        mockProduct.setId(1L);
        mockProduct.setName("Test Product");
        mockProduct.setPrice(100L);

        mockOrder = new OrderEntity();
        mockOrder.setId(1L);
        mockOrder.setUser(mockUser);
        mockOrder.setProduct(mockProduct);
    }

    @Test
    void testCreateOrder() {
        when(jwtUtils.decode("mockToken")).thenReturn(mockUser); //User valid
        when(productRepo.findById(1L)).thenReturn(Optional.of(mockProduct)); //Product exists

        orderService.createOrder(1L, "123 Street", "mockToken"); //createOrder

        verify(orderRepo, times(1)).save(any(OrderEntity.class)); //only 1 order saved
    }

    @Test
    void testGetAllOrders() {
        List<OrderEntity> orders = List.of(mockOrder);
        when(jwtUtils.decode("mockToken")).thenReturn(mockUser); //User valid
        when(orderRepo.findAllByUserEmail("user@example.com")).thenReturn(orders); //Returns 1 order

        List<OrderEntity> result = orderService.getAllOrders("mockToken");

        assertEquals(1, result.size());
        assertEquals(mockOrder, result.get(0)); //checks if the order is saved correctly
    }

    @Test
    void testRemoveOrderSuccessfully() {
        mockUser.getOrders().add(mockOrder);
        when(jwtUtils.decode("mockToken")).thenReturn(mockUser);
        when(orderRepo.findById(1L)).thenReturn(Optional.of(mockOrder)); //Order exists

        orderService.removeOrder(1L, "mockToken");

        verify(orderRepo).delete(mockOrder); //remove order
        verify(productRepo).save(mockProduct); //product updates
        verify(userRepo).save(mockUser); //user updates
    }

    @Test
    void testRemoveOrderUnauthorized() {
        UserEntity otherUser = new UserEntity();
        otherUser.setId(99L); // diff ID user/order
        when(jwtUtils.decode("mockToken")).thenReturn(otherUser);
        when(orderRepo.findById(1L)).thenReturn(Optional.of(mockOrder));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                orderService.removeOrder(1L, "mockToken"));

        assertEquals("Operation denied, Unauthorized", exception.getMessage());
    }

    @Test
    void testRemoveOrderNotFound() { //remove a non existent order
        when(jwtUtils.decode("mockToken")).thenReturn(mockUser);
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                orderService.removeOrder(1L, "mockToken"));

        assertEquals("Order not found", exception.getMessage()); 
    }

    @Test
    void testGenerateStepsCorrectly(){
        when(jwtUtils.decode("mockToken")).thenReturn(mockUser);
        when(productRepo.findById(1L)).thenReturn(Optional.of(mockProduct));

        orderService.createOrder(1L, "123 Street", "mockToken");

        verify(orderRepo).save(argThat(order -> {
        List<OrderStep> steps = order.getSteps();
        if (steps == null || steps.size() != 4) return false;

        return steps.get(0).getName().equals("Pedido recibido") &&
               steps.get(1).getName().equals("Saliendo del warehouse") &&
               steps.get(2).getName().equals("En reparto") &&
               steps.get(3).getName().equals("Entregado") &&
               steps.get(0).getDeadline().isBefore(steps.get(1).getDeadline()) &&
               steps.get(1).getDeadline().isBefore(steps.get(2).getDeadline()) &&
               steps.get(2).getDeadline().isBefore(steps.get(3).getDeadline());
        }));
    }
}
