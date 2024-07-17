package com.cg.onlinesweetmart.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cg.onlinesweetmart.entity.Category;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CategoryRepository;
import com.cg.onlinesweetmart.service.CategoryService;



@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Add a new category to the repository.
     *
     * @param category the category to be added
     * @return the saved category
     */
    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Retrieve all categories from the repository.
     *
     * @return list of all categories
     */
    @Override
    public List<Category> showAllCategory() {
        return categoryRepository.findAll();
    }

    /**
     * Update an existing category.
     *
     * @param category the category to be updated
     * @return the updated category
     */
    @Override
    public Category updateCategory(Category category) {
        // Check if the category exists
        if (!categoryRepository.existsById(category.getCategoryId())) {
            throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "Category not found with id " + category.getCategoryId());
        }
        return categoryRepository.save(category);
    }

    /**
     * Delete a category by its ID.
     *
     * @param categoryId the ID of the category to be deleted
     */
    @Override
    public void cancelCategory(int categoryId) {
        // Check if the category exists
        if (!categoryRepository.existsById(categoryId)) {
            throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "Category not found with id " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    /**
     * Retrieve a category by its ID.
     *
     * @param categoryId the ID of the category to be retrieved
     * @return the category
     */
    @Override
    public Category showCategory(int categoryId) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        if (!categoryOpt.isPresent()) {
            throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "Category not found with id " + categoryId);
        }
        return categoryOpt.get();
    }
}