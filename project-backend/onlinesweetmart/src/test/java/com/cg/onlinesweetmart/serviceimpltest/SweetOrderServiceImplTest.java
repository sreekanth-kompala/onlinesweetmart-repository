package com.cg.onlinesweetmart.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.cg.onlinesweetmart.entity.SweetOrder;
import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CartRepository;
import com.cg.onlinesweetmart.repository.SweetOrderRepository;
import com.cg.onlinesweetmart.repository.UserRepository;
import com.cg.onlinesweetmart.service.impl.SweetOrderServiceImpl;

public class SweetOrderServiceImplTest {

    @Mock
    private SweetOrderRepository sweetOrderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private SweetOrderServiceImpl sweetOrderServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for updating an existing sweet order.
     */
    @Test
    void testUpdateSweetOrder_SweetOrderExists() {
        // Arrange
        SweetOrder sweetOrder = new SweetOrder();
        sweetOrder.setSweetOrderId(1L);

        when(sweetOrderRepository.findById(1L)).thenReturn(Optional.of(sweetOrder)); // Mock sweet order repository to return an existing sweet order
        when(sweetOrderRepository.save(any(SweetOrder.class))).thenReturn(sweetOrder); // Mock save method

        // Act
        SweetOrder result = sweetOrderServiceImpl.updateSweetOrder(sweetOrder);

        // Assert
        assertNotNull(result); // Ensure that the result is not null
        assertEquals(1L, result.getSweetOrderId()); // Verify that the sweet order ID is correct
    }

    /**
     * Test case for updating a sweet order when the order is not found.
     */
    @Test
    void testUpdateSweetOrder_SweetOrderNotFound() {
        // Arrange
        SweetOrder sweetOrder = new SweetOrder();
        sweetOrder.setSweetOrderId(1L);

        when(sweetOrderRepository.findById(1L)).thenReturn(Optional.empty()); // Mock sweet order repository to return empty

        // Act & Assert
        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            sweetOrderServiceImpl.updateSweetOrder(sweetOrder); // Call update method and expect an exception
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus()); // Verify the HTTP status of the exception
        assertEquals("Sweet Order not found with ID: 1", exception.getMessage()); // Verify the exception message
    }

    /**
     * Test case for canceling an existing sweet order.
     */
    @Test
    void testCancelSweetOrder_SweetOrderExists() {
        // Arrange
        SweetOrder sweetOrder = new SweetOrder();
        sweetOrder.setSweetOrderId(1L);

        when(sweetOrderRepository.findById(1L)).thenReturn(Optional.of(sweetOrder)); // Mock sweet order repository to return an existing sweet order

        // Act
        SweetOrder result = sweetOrderServiceImpl.cancelSweetOrder(1L);

        // Assert
        assertNotNull(result); // Ensure that the result is not null
        assertEquals(1L, result.getSweetOrderId()); // Verify that the sweet order ID is correct
        verify(sweetOrderRepository).delete(any(SweetOrder.class)); // Verify that the delete method is called
    }

    /**
     * Test case for canceling a sweet order when the order is not found.
     */
    @Test
    void testCancelSweetOrder_SweetOrderNotFound() {
        // Arrange
        when(sweetOrderRepository.findById(1L)).thenReturn(Optional.empty()); // Mock sweet order repository to return empty

        // Act & Assert
        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
            sweetOrderServiceImpl.cancelSweetOrder(1L); // Call cancel method and expect an exception
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus()); // Verify the HTTP status of the exception
        assertEquals("Sweet Order not found with ID: 1", exception.getMessage()); // Verify the exception message
    }

    /**
     * Test case for showing all sweet orders when there are orders available.
     */
    @Test
    void testShowAllSweetOrders() {
        // Arrange
        List<SweetOrder> orders = new ArrayList<>();
        orders.add(new SweetOrder());
        when(sweetOrderRepository.findAll()).thenReturn(orders); // Mock sweet order repository to return a list with orders

        // Act
        List<SweetOrder> result = sweetOrderServiceImpl.showAllSweetOrders();

        // Assert
        assertNotNull(result); // Ensure that the result is not null
        assertFalse(result.isEmpty()); // Ensure that the result list is not empty
        assertEquals(1, result.size()); // Verify the size of the result list
    }

    /**
     * Test case for showing all sweet orders when there are no orders available.
     */
    @Test
    void testShowAllSweetOrders_NoOrders() {
        // Arrange
        when(sweetOrderRepository.findAll()).thenReturn(new ArrayList<>()); // Mock sweet order repository to return an empty list

        // Act
        List<SweetOrder> result = sweetOrderServiceImpl.showAllSweetOrders();

        // Assert
        assertNull(result); // Ensure that the result is null
    }
}


