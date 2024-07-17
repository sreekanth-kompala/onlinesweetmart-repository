package com.cg.onlinesweetmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinesweetmart.entity.Cart;
import com.cg.onlinesweetmart.service.impl.CartServiceImpl;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartServiceImpl cartServiceImpl;

    /**
     * Retrieve all carts.
     *
     * @return list of all carts
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Cart> showAllCart() {
        return cartServiceImpl.showAllCart();
    }

    /**
     * Add a product to a cart.
     *
     * @param cartId the ID of the cart
     * @param productId the ID of the product
     * @return the updated cart
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{cartId}/product/{productId}")
    public Cart addProductToCart(@PathVariable(value = "cartId") int cartId, @PathVariable(value = "productId") int productId) {
        return cartServiceImpl.addProductToCart(cartId, productId);
    }

    /**
     * Delete a product from a cart.
     *
     * @param cartId the ID of the cart
     * @param productId the ID of the product
     * @return a message indicating the product was deleted
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{cartId}/product/{productId}")
    public String deleteProductFromCart(@PathVariable(value = "cartId") int cartId, @PathVariable(value = "productId") int productId) {
        cartServiceImpl.deleteProductFromCart(cartId, productId);
        return "Product deleted from cart";
    }
}
 