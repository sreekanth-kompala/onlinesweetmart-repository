package com.cg.onlinesweetmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinesweetmart.entity.Category;
import com.cg.onlinesweetmart.service.impl.CategoryServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    /**
     * Add a new category.
     *
     * @param category the category to be added
     * @return the saved category
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Category addCategory(@Valid @RequestBody Category category) {
        return categoryServiceImpl.addCategory(category);
    }

    /**
     * Retrieve a category by its ID.
     *
     * @param categoryId the ID of the category to be retrieved
     * @return the category
     */
    @GetMapping("/{id}")
    public Category showCategory(@PathVariable(value = "id") int categoryId) {
        return categoryServiceImpl.showCategory(categoryId);
    }

    /**
     * Retrieve all categories.
     *
     * @return list of all categories
     */
    @GetMapping
    public List<Category> showAllCategory() {
        return categoryServiceImpl.showAllCategory();
    }

    /**
     * Update an existing category.
     *
     * @param category the category to be updated
     * @return the updated category
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Category updateCategory(@Valid @RequestBody Category category) {
        return categoryServiceImpl.updateCategory(category);
    }

    /**
     * Delete a category by its ID.
     *
     * @param categoryId the ID of the category to be deleted
     * @return a message indicating the category was deleted
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String cancelCategory(@PathVariable(value = "id") int categoryId) {
        categoryServiceImpl.cancelCategory(categoryId);
        return "Category deleted";
    }
}