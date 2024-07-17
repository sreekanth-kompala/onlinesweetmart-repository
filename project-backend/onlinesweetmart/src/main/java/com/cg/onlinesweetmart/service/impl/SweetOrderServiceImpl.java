package com.cg.onlinesweetmart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cg.onlinesweetmart.entity.Cart;
import com.cg.onlinesweetmart.entity.Product;
import com.cg.onlinesweetmart.entity.SweetOrder;
import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CartRepository;
import com.cg.onlinesweetmart.repository.SweetOrderRepository;
import com.cg.onlinesweetmart.repository.UserRepository;
import com.cg.onlinesweetmart.service.SweetOrderService;

/**
 * Implementation of the SweetOrderService interface for managing sweet orders.
 */
@Service
public class SweetOrderServiceImpl implements SweetOrderService {

    @Autowired
    private SweetOrderRepository sweetOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    /**
     * Adds a new sweet order for a user.
     *
     * @param userId     The ID of the user placing the order.
     * @param sweetOrder The sweet order to be added.
     * @return The saved sweet order.
     */
    @Override
    public SweetOrder addSweetOrder(Long userId, SweetOrder sweetOrder) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Cart cart = user.getCart();
            
            if(cart.getGrandTotal() == 0) {
            	throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "Cart is empty. Add any products before checkout.");
            }

            // Create a copy of products to avoid modifying the original ones in the cart
            List<Product> productListCopy = new ArrayList<>();
            for (Product product : cart.getListProduct()) {
                Product productCopy = new Product();
                productCopy.setProductId(product.getProductId());
                productCopy.setName(product.getName());
                productCopy.setPrice(product.getPrice());
                productListCopy.add(productCopy);
            }

            sweetOrder.setListProduct(productListCopy);
            sweetOrder.setTotalCost(cart.getGrandTotal());
            sweetOrder.setUser(user);
            
            // Save the sweetOrder to generate a unique ID
            SweetOrder savedOrder = sweetOrderRepository.save(sweetOrder);

            // Add the saved order to the user's set
            Set<SweetOrder> sweetOrderSet = user.getSweetOrder();
            sweetOrderSet.add(savedOrder);
            user.setSweetOrder(sweetOrderSet);

            // Clear the user's cart after placing the order
            cart.getListProduct().clear();
            cart.setGrandTotal(0.0);
            cart.setProductCount(0);
            cartRepository.save(cart);

            userRepository.save(user);

            return savedOrder;
        } else {
            // If the user is not found, return null
        	throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "Sweet Order not placed successfully.");
        }
    }

    /**
     * Updates an existing sweet order.
     *
     * @param sweetOrder The sweet order to be updated.
     * @return The updated sweet order.
     */
    @Override
    public SweetOrder updateSweetOrder(SweetOrder sweetOrder) {
        Optional<SweetOrder> optSweetOrd = sweetOrderRepository.findById(sweetOrder.getSweetOrderId());
        if (optSweetOrd.isPresent()) {
            return sweetOrderRepository.save(sweetOrder);
        } else {
            throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "Sweet Order not found with ID: " + sweetOrder.getSweetOrderId());
        }
    }

    /**
     * Cancels a sweet order by its ID.
     *
     * @param sweetOrderId The ID of the sweet order to be canceled.
     * @return The canceled sweet order.
     */
    @Override
    public SweetOrder cancelSweetOrder(Long sweetOrderId) {
        Optional<SweetOrder> optSweetOrder = sweetOrderRepository.findById(sweetOrderId);
        if (optSweetOrder.isPresent()) {
            SweetOrder canceledOrder = optSweetOrder.get();
            sweetOrderRepository.delete(canceledOrder);
            return canceledOrder;
        } else {
            throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "Sweet Order not found with ID: " + sweetOrderId);
        }
    }

    /**
     * Retrieves a list of all sweet orders.
     *
     * @return A list of all sweet orders.
     */
    @Override
    public List<SweetOrder> showAllSweetOrders() {
        List<SweetOrder> listSweetOrder = sweetOrderRepository.findAll();
        if (listSweetOrder.isEmpty()) {
            return null; // Consider returning an empty list instead of null
        }
        return listSweetOrder;
    }
}