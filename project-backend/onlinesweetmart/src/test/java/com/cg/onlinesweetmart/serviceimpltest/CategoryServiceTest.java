package com.cg.onlinesweetmart.serviceimpltest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.cg.onlinesweetmart.entity.Category;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.CategoryRepository;
import com.cg.onlinesweetmart.service.impl.CategoryServiceImpl;


public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;

	private Category category;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		// Initialize a common Category object to be used in the tests
		category = new Category(1, "Sweets");
	}

	@Test
	void testAddCategory_Success() {
		// Given: Mock the categoryRepository.save() method to return the category
		// object
		when(categoryRepository.save(category)).thenReturn(category);

		// When: Call the addCategory method
		Category result = categoryServiceImpl.addCategory(category);

		// Then: Assert the result and verify interactions
		assertNotNull(result); // Ensure that the result is not null
		assertEquals(1, result.getCategoryId()); // Check if the categoryId matches
		assertEquals("Sweets", result.getName()); // Check if the name matches

		verify(categoryRepository, times(1)).save(category); // Verify that save was called once
	}

	@Test
	void testShowAllCategory_Success() {
		// Given: Mock the categoryRepository.findAll() method to return a list of
		// categories
		List<Category> categories = Arrays.asList(new Category(1, "Sweets"), new Category(2, "Chocolates"));
		when(categoryRepository.findAll()).thenReturn(categories);

		// When: Call the showAllCategory method
		List<Category> result = categoryServiceImpl.showAllCategory();

		// Then: Assert the result and verify interactions
		assertNotNull(result); // Ensure that the result is not null
		assertEquals(2, result.size()); // Check if the list size matches
		assertEquals(1, result.get(0).getCategoryId()); // Check the first category's ID
		assertEquals("Sweets", result.get(0).getName()); // Check the first category's name

		verify(categoryRepository, times(1)).findAll(); // Verify that findAll was called once
	}

	@Test
	void testUpdateCategory_Success() {
		// Given: Mock the categoryRepository.existsById() and save() methods
		when(categoryRepository.existsById(category.getCategoryId())).thenReturn(true);
		when(categoryRepository.save(category)).thenReturn(category);

		// When: Call the updateCategory method
		Category result = categoryServiceImpl.updateCategory(category);

		// Then: Assert the result and verify interactions
		assertNotNull(result); // Ensure that the result is not null
		assertEquals(1, result.getCategoryId()); // Check if the categoryId matches
		assertEquals("Sweets", result.getName()); // Check if the name matches

		verify(categoryRepository, times(1)).existsById(category.getCategoryId()); // Verify existsById was called once
		verify(categoryRepository, times(1)).save(category); // Verify save was called once
	}

	@Test
	void testUpdateCategory_Failure() {
		// Given: Mock the categoryRepository.existsById() method to return false
		when(categoryRepository.existsById(category.getCategoryId())).thenReturn(false);

		// When: Call the updateCategory method and expect an exception
		SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
			categoryServiceImpl.updateCategory(category);
		});

		// Then: Assert the exception and verify interactions
		assertNotNull(exception); // Ensure that the exception is not null
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus()); // Check the status of the exception
		assertEquals("Category not found with id " + category.getCategoryId(), exception.getMessage()); // Check the
																										// message

		verify(categoryRepository, times(1)).existsById(category.getCategoryId()); // Verify existsById was called once
		verify(categoryRepository, times(0)).save(any(Category.class)); // Verify save was never called
	}

	@Test
	void testCancelCategory_Success() {
		// Given: Mock the categoryRepository.existsById() method to return true
		int categoryId = 1;
		when(categoryRepository.existsById(categoryId)).thenReturn(true);

		// When: Call the cancelCategory method
		categoryServiceImpl.cancelCategory(categoryId);

		// Then: Verify interactions
		verify(categoryRepository, times(1)).existsById(categoryId); // Verify existsById was called once
		verify(categoryRepository, times(1)).deleteById(categoryId); // Verify deleteById was called once
	}

	@Test
	void testCancelCategory_Failure() {
		// Given: Mock the categoryRepository.existsById() method to return false
		int categoryId = 1;
		when(categoryRepository.existsById(categoryId)).thenReturn(false);

		// When: Call the cancelCategory method and expect an exception
		SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
			categoryServiceImpl.cancelCategory(categoryId);
		});

		// Then: Assert the exception and verify interactions
		assertNotNull(exception); // Ensure that the exception is not null
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus()); // Check the status of the exception
		assertEquals("Category not found with id " + categoryId, exception.getMessage()); // Check the message

		verify(categoryRepository, times(1)).existsById(categoryId); // Verify existsById was called once
		verify(categoryRepository, times(0)).deleteById(categoryId); // Verify deleteById was never called
	}
	
	@Test
    public void testShowCategory() {
        // Mocking data
        int categoryId = 1;
        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setName("Test Category");

        // Mocking repository behavior
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Test the service method
        Category result = categoryServiceImpl.showCategory(categoryId);

        // Assertions
        assertNotNull(result);
        assertEquals(categoryId, result.getCategoryId());
        assertEquals("Test Category", result.getName());
    }

    

}
