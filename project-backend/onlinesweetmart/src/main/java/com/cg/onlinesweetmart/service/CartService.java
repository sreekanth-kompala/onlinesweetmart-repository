package com.cg.onlinesweetmart.service;

import java.util.List;

import com.cg.onlinesweetmart.entity.Cart;

public interface CartService {

	/**
	 * Retrieve all carts from the repository.
	 *
	 * @return List of all carts
	 */
	List<Cart> showAllCart();

	/**
	 * Add a product to the cart by their IDs.
	 *
	 * @param cartId the ID of the cart
	 * @param productId the ID of the product
	 * @return the updated Cart
	 */
	Cart addProductToCart(int cartId, int productId);

	/**
	 * Remove a product from the cart by their IDs.
	 *
	 * @param cartId the ID of the cart
	 * @param productId the ID of the product
	 * @return the updated Cart
	 */
	Cart deleteProductFromCart(int cartId, int productId);

	/**
	 * Add a new cart to the repository.
	 *
	 * @param cart the Cart object to be added
	 * @return the saved Cart
	 */
	Cart addCart(Cart cart);

}
