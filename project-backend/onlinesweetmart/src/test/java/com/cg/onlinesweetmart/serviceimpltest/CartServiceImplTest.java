package com.cg.onlinesweetmart.serviceimpltest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.cg.onlinesweetmart.entity.Cart;
import com.cg.onlinesweetmart.entity.Product;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CartRepository;
import com.cg.onlinesweetmart.repository.ProductRepository;
import com.cg.onlinesweetmart.service.impl.CartServiceImpl;

public class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartServiceImpl;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    private Cart cart;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a product instance
        product = new Product();
        product.setProductId(1);
        product.setName("Product 1");
        product.setPrice(100);

        // Initialize a cart instance with the product
        cart = new Cart();
        cart.setCartId(1);
        cart.setGrandTotal(100);
        cart.setListProduct(new ArrayList<>(Arrays.asList(product)));
        cart.setProductCount(1);
    }

    // Test case for retrieving all carts
    @Test
    public void testShowAllCart() {
        when(cartRepository.findAll()).thenReturn(Arrays.asList(cart));

        List<Cart> carts = cartServiceImpl.showAllCart();

        assertNotNull(carts);
        assertEquals(1, carts.size());
        assertEquals(1, carts.get(0).getCartId());
    }

    // Test case for adding a product to a cart successfully
    @Test
    public void testAddProductToCart() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartServiceImpl.addProductToCart(1, 1);

        assertNotNull(updatedCart);
        assertEquals(2, updatedCart.getProductCount());
        assertEquals(200, updatedCart.getGrandTotal());
    }

    // Test case for adding a product to a cart when the product is not found
    @Test
    public void testAddProductToCart_ProductNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            cartServiceImpl.addProductToCart(1, 1);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Product not found with id 1", exception.getMessage());
    }

    // Test case for adding a product to a cart when the cart is not found
    @Test
    public void testAddProductToCart_CartNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartRepository.findById(1)).thenReturn(Optional.empty());

        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            cartServiceImpl.addProductToCart(1, 1);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Cart not found with id 1", exception.getMessage());
    }

    // Test case for removing a product from a cart successfully
    @Test
    public void testDeleteProductFromCart() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartServiceImpl.deleteProductFromCart(1, 1);

        assertNotNull(updatedCart);
        assertEquals(0, updatedCart.getProductCount());
        assertEquals(0, updatedCart.getGrandTotal());
    }

    // Test case for removing a product from a cart when the product is not found
    @Test
    public void testDeleteProductFromCart_ProductNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            cartServiceImpl.deleteProductFromCart(1, 1);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Product not found with id 1", exception.getMessage());
    }

    // Test case for removing a product from a cart when the cart is not found
    @Test
    public void testDeleteProductFromCart_CartNotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(cartRepository.findById(1)).thenReturn(Optional.empty());

        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            cartServiceImpl.deleteProductFromCart(1, 1);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Cart not found with id 1", exception.getMessage());
    }

    // Test case for adding a new cart successfully
    @Test
    public void testAddCart() {
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart savedCart = cartServiceImpl.addCart(cart);

        assertNotNull(savedCart);
        assertEquals(1, savedCart.getCartId());
    }
}
