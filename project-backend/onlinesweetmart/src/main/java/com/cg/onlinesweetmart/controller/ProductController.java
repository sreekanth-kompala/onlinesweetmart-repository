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

import com.cg.onlinesweetmart.entity.Product;
import com.cg.onlinesweetmart.service.impl.ProductServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    /**
     * Retrieve all products.
     *
     * @return list of all products
     */
    @GetMapping
    public List<Product> showAllProduct() {
        return productServiceImpl.showAllProduct();
    }

    /**
     * Retrieve products by category ID.
     *
     * @param categoryId the ID of the category
     * @return list of products in the specified category
     */
    @GetMapping("/{id}")
    public List<Product> getProductsByCategoryId(@PathVariable(value = "id") int categoryId) {
        return productServiceImpl.getProductByCategoryId(categoryId);
    }
    
    @GetMapping("/product/{id}")
    public Product getProductByProductId(@PathVariable(value = "id") int productId) {
    	return productServiceImpl.getProductByProductId(productId);
    }
    
    @GetMapping("/productName/{name}")
    public List<Product> getProductsByProductName(@PathVariable(value = "name") String name) {
    	return productServiceImpl.getProductsByProductName(name);
    }

    /**
     * Add a new product to a specific category.
     *
     * @param product the product to be added
     * @param categoryId the ID of the category
     * @return the saved product
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public Product addProduct(@Valid @RequestBody Product product, @PathVariable(value = "id") int categoryId) {
        return productServiceImpl.addProduct(product, categoryId);
    }

    /**
     * Update an existing product.
     *
     * @param product the product to be updated
     * @return the updated product
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public Product updateProduct(@Valid @RequestBody Product product) {
        return productServiceImpl.updateProduct(product);
    }

    /**
     * Delete a product by its ID.
     *
     * @param productId the ID of the product to be deleted
     * @return a message indicating the product was deleted
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String cancelProduct(@PathVariable(value = "id") int productId) {
        productServiceImpl.cancelProduct(productId);
        return "Product deleted";
    }
}