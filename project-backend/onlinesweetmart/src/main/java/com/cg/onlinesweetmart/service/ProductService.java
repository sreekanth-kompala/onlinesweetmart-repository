package com.cg.onlinesweetmart.service;

import java.util.List;

import com.cg.onlinesweetmart.entity.Product;

public interface ProductService {

	/**
	 * Add a new product to a specific category.
	 *
	 * @param product the product to be added
	 * @param categoryId the ID of the category
	 * @return the saved product
	 */
	Product addProduct(Product product, int categoryId);

	/**
	 * Update an existing product.
	 *
	 * @param product the product to be updated
	 * @return the updated product
	 */
	Product updateProduct(Product product);

	/**
	 * Delete a product by its ID.
	 *
	 * @param productId the ID of the product to be deleted
	 */
	void cancelProduct(int productId);

	/**
	 * Retrieve all products.
	 *
	 * @return list of all products
	 */
	List<Product> showAllProduct();

	/**
	 * Retrieve products by category ID.
	 *
	 * @param categoryId the ID of the category
	 * @return list of products in the specified category
	 */
	List<Product> getProductByCategoryId(int categoryId);

}
