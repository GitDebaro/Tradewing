package com.tradewing.servicetests;

import org.springframework.http.ResponseEntity;

import com.tradewing.models.ProductEntity;
import com.tradewing.models.UserEntity;
import com.tradewing.services.impl.ProductServiceImpl;
import com.tradewing.repos.ProductRepo;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerImplTest{

    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    ProductEntity product1;
    ProductEntity product2;
    UserEntity seller;

    @BeforeEach
    void setUp() throws Exception {
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

        seller = new UserEntity();
        seller.setId(1L);
        seller.setName("Test User");
        seller.setEmail("test@user.com");
        seller.setPassword("sha256");
    }

    @Test
    void getProductsByNameReturnsList() {
        String name = "Product";
        List<ProductEntity> mockList = List.of(product1,product2);
        when(productRepo.findByNameContainingIgnoreCase(name)).thenReturn(mockList);

        List<ProductEntity> result = productService.getProductsByName(name);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("https://placehold.co/200x200?text=Producto1",result.get(0).getImage());
        assertEquals("Product Test 2", result.get(1).getName());
        assertEquals("https://placehold.co/400x400?text=Producto2",result.get(1).getImage());
        verify(productRepo).findByNameContainingIgnoreCase(name);
    }

    @Test
    void getProductsByNameReturnsListNotMatch() {
        String name = "]tEsT";
        List<ProductEntity> mockList = List.of();
        when(productRepo.findByNameContainingIgnoreCase(name)).thenReturn(mockList);

        List<ProductEntity> result = productService.getProductsByName(name);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepo).findByNameContainingIgnoreCase(name);
    }

    /*
    EXPECTED REFACTOR of JWT Utils class
    TO DO: addProduct Tests

    TO DO: removeProduct tests
    */
    
    @Test
    void getProductsByIdReturnsOptional() {
        when(productRepo.findById(1L)).thenReturn(Optional.of(product1));

        Optional<ProductEntity> result = productService.getProductsById(1L);

        assertTrue(result.isPresent());
        assertEquals(product1, result.get());
        verify(productRepo).findById(1L);
    }

    @Test
    void getProductsByIdReturnsEmptyOptional() {
        when(productRepo.findById(9999L)).thenReturn(Optional.empty());

        Optional<ProductEntity> result = productService.getProductsById(9999L);

        assertTrue(result.isEmpty());
        verify(productRepo).findById(9999L);
    }

    @Test
    void getAllProductsReturnsList() {
        List<ProductEntity> products = List.of(product1, product2);
        when(productRepo.findAll()).thenReturn(products);

        List<ProductEntity> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("https://placehold.co/200x200?text=Producto1",result.get(0).getImage());
        assertEquals("Product Test 2", result.get(1).getName());
        assertEquals("https://placehold.co/400x400?text=Producto2",result.get(1).getImage());
        verify(productRepo).findAll();
    }

    @Test
    void getAllProductsReturnsListEmpty() {
        List<ProductEntity> products = List.of();
        when(productRepo.findAll()).thenReturn(products);

        List<ProductEntity> result = productService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepo).findAll();
    }

    @Test
    void testFindBySellerReturnsList() {
        List<ProductEntity> products = List.of(product2);

        when(productRepo.findBySeller(seller)).thenReturn(products);

        List<ProductEntity> result = productService.findBySeller(seller);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Product Test 2", result.get(0).getName());
        assertEquals("https://placehold.co/400x400?text=Producto2",result.get(0).getImage());
        verify(productRepo).findBySeller(seller);
    }

    @Test
    void testFindBySellerReturnsListEmpty() {
        List<ProductEntity> products = List.of();

        when(productRepo.findBySeller(seller)).thenReturn(products);

        List<ProductEntity> result = productService.findBySeller(seller);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepo).findBySeller(seller);
    }
}