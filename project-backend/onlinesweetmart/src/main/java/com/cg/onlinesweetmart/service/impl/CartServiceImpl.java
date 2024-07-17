package com.cg.onlinesweetmart.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cg.onlinesweetmart.entity.Cart;
import com.cg.onlinesweetmart.entity.Product;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CartRepository;
import com.cg.onlinesweetmart.repository.ProductRepository;
import com.cg.onlinesweetmart.service.CartService;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieve all carts from the repository.
     *
     * @return List of all carts
     */
    @Override
    public List<Cart> showAllCart() {
        return cartRepository.findAll();
    }

    /**
     * Add a product to the cart by their IDs.
     *
     * @param cartId the ID of the cart
     * @param productId the ID of the product
     * @return the updated Cart
     */
    @Override
    public Cart addProductToCart(int cartId, int productId) {

        // Find the product by its ID
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            throw new SweetMartAPIException(HttpStatus.BAD_REQUEST, "Product not found with id " + productId);
        }
        Product product = productOpt.get();

        // Find the cart by its ID
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            throw new SweetMartAPIException(HttpStatus.BAD_REQUEST, "Cart not found with id " + cartId);
        }
        Cart cart = cartOpt.get();

        // Add the product to the cart
        List<Product> listProduct = cart.getListProduct();
        listProduct.add(product);

        // Update the cart details
        cart.setListProduct(listProduct);
        cart.setProductCount(listProduct.size());
        cart.setGrandTotal(cart.getGrandTotal() + product.getPrice());

        // Save and return the updated cart
        return cartRepository.save(cart);
    }

    /**
     * Remove a product from the cart by their IDs.
     *
     * @param cartId the ID of the cart
     * @param productId the ID of the product
     * @return the updated Cart
     */
    @Override
    public Cart deleteProductFromCart(int cartId, int productId) {

        // Find the product by its ID
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            throw new SweetMartAPIException(HttpStatus.BAD_REQUEST, "Product not found with id " + productId);
        }
        Product product = productOpt.get();

        // Find the cart by its ID
        Optional<Cart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            throw new SweetMartAPIException(HttpStatus.BAD_REQUEST, "Cart not found with id " + cartId);
        }
        Cart cart = cartOpt.get();

        // Remove the product from the cart
        List<Product> listProduct = cart.getListProduct();
        listProduct.remove(product);

        // Update the cart details
        cart.setListProduct(listProduct);
        cart.setProductCount(listProduct.size());
        cart.setGrandTotal(cart.getGrandTotal() - product.getPrice());

        // Save and return the updated cart
        return cartRepository.save(cart);
    }

    /**
     * Add a new cart to the repository.
     *
     * @param cart the Cart object to be added
     * @return the saved Cart
     */
    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }
}