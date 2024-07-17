package com.cg.onlinesweetmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinesweetmart.entity.SweetOrder;
import com.cg.onlinesweetmart.service.impl.SweetOrderServiceImpl;

/**
 * Controller class for managing sweet orders.
 */
@RestController
@RequestMapping("/api/sweetorders")
public class SweetOrderController {
    
    @Autowired
    private SweetOrderServiceImpl sweetOrderServiceImpl;
    
    /**
     * Endpoint for adding a sweet order.
     *
     * @param userId     The ID of the user placing the order.
     * @param sweetOrder The sweet order to be added.
     * @return ResponseEntity with the added sweet order and HTTP status CREATED.
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{userId}")
    public ResponseEntity<SweetOrder> addSweetOrder(@PathVariable Long userId, @RequestBody SweetOrder sweetOrder) {
        SweetOrder addedOrder = sweetOrderServiceImpl.addSweetOrder(userId, sweetOrder);
        return new ResponseEntity<>(addedOrder, HttpStatus.CREATED);
    }
    
    /**
     * Endpoint for updating an existing sweet order.
     *
     * @param sweetOrder The sweet order to be updated.
     * @return ResponseEntity with the updated sweet order and HTTP status OK.
     * @throws OrderNotFoundException If the sweet order to be updated is not found.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<SweetOrder> updateSweetOrder(@RequestBody SweetOrder sweetOrder) {
        SweetOrder updatedOrder = sweetOrderServiceImpl.updateSweetOrder(sweetOrder);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
    
    /**
     * Endpoint for canceling a sweet order by its ID.
     *
     * @param sweetOrderId The ID of the sweet order to be canceled.
     * @return ResponseEntity with the canceled sweet order and HTTP status OK.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{sweetOrderId}")
    public ResponseEntity<SweetOrder> cancelSweetOrder(@PathVariable Long sweetOrderId) {
        SweetOrder canceledOrder = sweetOrderServiceImpl.cancelSweetOrder(sweetOrderId);
        return new ResponseEntity<>(canceledOrder, HttpStatus.OK);
    }
    
    /**
     * Endpoint for retrieving a list of all sweet orders.
     *
     * @return ResponseEntity with the list of all sweet orders and HTTP status OK.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SweetOrder>> showAllSweetOrders() {
        List<SweetOrder> sweetOrderList = sweetOrderServiceImpl.showAllSweetOrders();
        return new ResponseEntity<>(sweetOrderList, HttpStatus.OK);
    }
}