package com.tradewing.controllertests;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.tradewing.models.ProductEntity;
import com.tradewing.services.ProductService;
import com.tradewing.controllers.impl.ProductControllerImpl;
import com.tradewing.dto.AddProductRequest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductControllerImplTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductControllerImpl productController;

    private ProductEntity product1;
    private ProductEntity product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("Product Test");
        product1.setDescription("Test");
        product1.setImage("https://placehold.co/200x200?text=Producto1");

        product2 = new ProductEntity();
        product2.setId(2L);
        product2.setName("Product Test 2");
        product2.setDescription("Test");
        product2.setImage("https://placehold.co/400x400?text=Producto2");
    }

    @Test
    void getProductsByNameReturnsList(){
        String name = "Product Test";
        List<ProductEntity> mockList = List.of(product1);
        when(productService.getProductsByName(name)).thenReturn(mockList);

        List<ProductEntity> result = productController.getProductsByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product Test", result.get(0).getName());
        assertEquals("https://placehold.co/200x200?text=Producto1",result.get(0).getImage());
        verify(productService).getProductsByName(name);
    }

    @Test
    void getProductsByIdReturnsSuccessReturnsResponse() {
        Long id = 1L;
        when(productService.getProductsById(id)).thenReturn(Optional.of(product1));

        ResponseEntity<ProductEntity> response = productController.getProductsById(id);

        assertNotNull(response);
        assertEquals(ResponseEntity.ok(product1), response);
        verify(productService).getProductsById(id);
    }

    @Test
    void getProductsByIdNotFoundReturnsResponse() {
        Long id = 999L;
        when(productService.getProductsById(id)).thenReturn(Optional.empty());

        ResponseEntity<ProductEntity> response = productController.getProductsById(id);

        assertNotNull(response);
        assertEquals(ResponseEntity.notFound().build(), response);
        verify(productService).getProductsById(id);
    }


    @Test
    void addProductReturnsVoid() {
        AddProductRequest request = new AddProductRequest();
        String authHeader = "Bearer eyTestToken";
        String token = authHeader.replace("Bearer ","");

        doNothing().when(productService).addProduct(request, token);

        productController.addProduct(request, authHeader);

        verify(productService).addProduct(request, token);
    }
    
    @Test
    void removeProductReturnsVoid() {
        Long productId = 1L;
        String authHeader = "Bearer eyTestToken";
        String token = authHeader.replace("Bearer ","");

        doNothing().when(productService).removeProduct(productId, token);

        productController.removeProduct(productId, authHeader);

        verify(productService).removeProduct(productId, token);
    }

    @Test
    void getAllProductsReturnsList() {
        List<ProductEntity> products = List.of(product1,product2);
        when(productService.getAllProducts()).thenReturn(products);

        List<ProductEntity> result = productController.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("https://placehold.co/200x200?text=Producto1",result.get(0).getImage());
        assertEquals("Product Test 2", result.get(1).getName());
        assertEquals("https://placehold.co/400x400?text=Producto2",result.get(1).getImage());
        verify(productService).getAllProducts();
    }

}