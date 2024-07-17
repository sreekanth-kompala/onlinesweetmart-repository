package com.cg.onlinesweetmart.serviceimpltest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.cg.onlinesweetmart.entity.Category;
import com.cg.onlinesweetmart.entity.Product;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CategoryRepository;
import com.cg.onlinesweetmart.repository.ProductRepository;
import com.cg.onlinesweetmart.service.impl.ProductServiceImpl;

public class ProductServiceImplTest {

    // Inject the ProductServiceImpl into the test class
    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    // Mock the dependencies of ProductServiceImpl
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private Product product;
    private Category category;

    // Set up initial data and initialize mocks before each test
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a category instance
        category = new Category();
        category.setCategoryId(1);
        category.setName("Category 1");

        // Create a product instance
        product = new Product();
        product.setProductId(1);
        product.setName("Product 1");
        product.setPhotoPath("photo/path");
        product.setPrice(100);
        product.setDescription("Description");
        product.setAvailable(true);
        product.setCategory(category);
    }

    // Test case for adding a product successfully
    @Test
    public void testAddProduct() {
        // Mock the behavior of categoryRepository and productRepository
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Call the method to be tested
        Product savedProduct = productServiceImpl.addProduct(product, 1);

        // Assert the results
        assertNotNull(savedProduct);
        assertEquals("Product 1", savedProduct.getName());
        assertEquals(category, savedProduct.getCategory());
    }

    // Test case for adding a product when the category is not found
    @Test
    public void testAddProduct_CategoryNotFound() {
        // Mock the behavior of categoryRepository
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        // Assert that the correct exception is thrown
        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            productServiceImpl.addProduct(product, 1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Category not found with id 1", exception.getMessage());
    }

    // Test case for updating a product successfully
    @Test
    public void testUpdateProduct() {
        // Mock the behavior of productRepository
        when(productRepository.existsById(1)).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Call the method to be tested
        Product updatedProduct = productServiceImpl.updateProduct(product);

        // Assert the results
        assertNotNull(updatedProduct);
        assertEquals("Product 1", updatedProduct.getName());
    }

    // Test case for updating a product when the product is not found
    @Test
    public void testUpdateProduct_ProductNotFound() {
        // Mock the behavior of productRepository
        when(productRepository.existsById(1)).thenReturn(false);

        // Assert that the correct exception is thrown
        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            productServiceImpl.updateProduct(product);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Product not found with id 1", exception.getMessage());
    }

    // Test case for canceling (deleting) a product successfully
    @Test
    public void testCancelProduct() {
        // Mock the behavior of productRepository
        when(productRepository.existsById(1)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1);

        // Call the method to be tested
        productServiceImpl.cancelProduct(1);

        // Verify that the deleteById method was called exactly once
        verify(productRepository, times(1)).deleteById(1);
    }

    // Test case for canceling (deleting) a product when the product is not found
    @Test
    public void testCancelProduct_ProductNotFound() {
        // Mock the behavior of productRepository
        when(productRepository.existsById(1)).thenReturn(false);

        // Assert that the correct exception is thrown
        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            productServiceImpl.cancelProduct(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Product not found with id 1", exception.getMessage());
    }

    // Test case for showing all products
    @Test
    public void testShowAllProduct() {
        // Mock the behavior of productRepository
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        // Call the method to be tested
        List<Product> products = productServiceImpl.showAllProduct();

        // Assert the results
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product 1", products.get(0).getName());
    }

    // Test case for getting products by category ID
    @Test
    public void testGetProductByCategoryId() {
        // Mock the behavior of productRepository
        when(productRepository.findByCategoryCategoryId(1)).thenReturn(Arrays.asList(product));

        // Call the method to be tested
        List<Product> products = productServiceImpl.getProductByCategoryId(1);

        // Assert the results
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product 1", products.get(0).getName());
    }
}
