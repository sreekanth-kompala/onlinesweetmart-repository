package com.cg.onlinesweetmart.service;

import java.util.List;

import com.cg.onlinesweetmart.entity.Category;

public interface CategoryService {

	/**
	 * Add a new category to the repository.
	 *
	 * @param category the category to be added
	 * @return the saved category
	 */
	Category addCategory(Category category);

	/**
	 * Retrieve all categories from the repository.
	 *
	 * @return list of all categories
	 */
	List<Category> showAllCategory();

	/**
	 * Update an existing category.
	 *
	 * @param category the category to be updated
	 * @return the updated category
	 */
	Category updateCategory(Category category);

	/**
	 * Delete a category by its ID.
	 *
	 * @param categoryId the ID of the category to be deleted
	 */
	void cancelCategory(int categoryId);

	/**
	 * Retrieve a category by its ID.
	 *
	 * @param categoryId the ID of the category to be retrieved
	 * @return the category
	 */
	Category showCategory(int categoryId);

}
