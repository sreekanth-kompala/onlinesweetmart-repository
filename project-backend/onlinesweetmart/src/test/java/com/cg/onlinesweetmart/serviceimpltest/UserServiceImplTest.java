package com.cg.onlinesweetmart.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.cg.onlinesweetmart.dto.UserResponseDto;
import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.UserRepository;

import com.cg.onlinesweetmart.service.impl.UserServiceImpl;

public class UserServiceImplTest {

	@Mock
	private UserRepository userrepository;

	@InjectMocks
	private UserServiceImpl userserviceimpl;

	@BeforeEach
	void Setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testShowAllUsers() {
		User user1 = new User();
		user1.setUserId(1L);
		user1.setUsername("Gino");
		user1.setEmail("GInod3#@gmial.com");

		User user2 = new User();
		user2.setUserId(2L);
		user2.setUsername("mino");
		user2.setEmail("Gkdnod3#@gmial.com");

		when(userrepository.findAll()).thenReturn(Arrays.asList(user1, user2));

		List<UserResponseDto> result = userserviceimpl.showAllUsers();

		assertEquals(1L, result.get(0).getUserId());
		assertEquals("Gino", result.get(0).getUsername());
		assertEquals("GInod3#@gmial.com", result.get(0).getUserEmail());

		assertEquals(2L, result.get(1).getUserId());
		assertEquals("mino", result.get(1).getUsername());
		assertEquals("Gkdnod3#@gmial.com", result.get(1).getUserEmail());

		verify(userrepository, times(1)).findAll();
	}

	@Test
	void testUpdateUser_Success() {
		// Given
		User user = new User(1L, "username1", "user1@example.com", "password", null, null, null);
		User updatedUser = new User(1L, "username1", "user1@example.com", "password", null, null, null);

		when(userrepository.existsById(user.getUserId())).thenReturn(true);
		when(userrepository.save(user)).thenReturn(updatedUser);

		// When
		UserResponseDto result = userserviceimpl.updateUser(user);

		// Then
		assertNotNull(result);
		assertEquals(1L, result.getUserId());
		assertEquals("username1", result.getUsername());
		assertEquals("user1@example.com", result.getUserEmail());

		verify(userrepository, times(1)).existsById(user.getUserId());
		verify(userrepository, times(1)).save(user);
	}

	@Test
	void testUpdateUser_Failure() {
		// Given
		User user = new User(1L, "username1", "user1@example.com", "password", null, null, null);

		when(userrepository.existsById(user.getUserId())).thenReturn(false);

		// When
		SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
			userserviceimpl.updateUser(user);
		});

		// Then
		assertNotNull(exception);
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
		assertEquals("User not found with ID: 1", exception.getMessage());

		verify(userrepository, times(1)).existsById(user.getUserId());
		verify(userrepository, times(0)).save(any(User.class));
	}
	
	 @Test
	    void testCancelUser_Success() {
	        // Given
	        long userId = 1L;

	        when(userrepository.existsById(userId)).thenReturn(true);

	        // When
	        userserviceimpl.cancelUser(userId);

	        // Then
	        verify(userrepository, times(1)).existsById(userId);
	        verify(userrepository, times(1)).deleteById(userId);
	    }

	    @Test
	    void testCancelUser_Failure() {
	        // Given
	        long userId = 1L;

	        when(userrepository.existsById(userId)).thenReturn(false);

	        // When
	        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
	            userserviceimpl.cancelUser(userId);
	        });

	        // Then
	        assertNotNull(exception);
	        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
	        assertEquals("User not found with ID: " + userId, exception.getMessage());

	        verify(userrepository, times(1)).existsById(userId);
	        verify(userrepository, times(0)).deleteById(userId);
	    }
	    @Test
	    void testShowUser_Success() {
	        // Given
	        long userId = 1L;
	        User user = new User(1L, "username1", "user1@example.com", "password", null, null, null);
	        
	        // Mock the userRepository to return an Optional of user when findById is called with userId
	        when(userrepository.findById(userId)).thenReturn(Optional.of(user));

	        // When
	        User result = userserviceimpl.showUser(userId);

	        // Then
	        assertNotNull(result); // Ensure that the result is not null
	        assertEquals(1L, result.getUserId()); // Check if the userId matches
	        assertEquals("username1", result.getUsername()); // Check if the username matches
	        assertEquals("user1@example.com", result.getEmail()); // Check if the email matches

	        // Verify that the userRepository's findById method was called once with the given userId
	        verify(userrepository, times(1)).findById(userId);
	    }

	    @Test
	    void testShowUser_Failure() {
	        // Given
	        long userId = 1L;

	        // Mock the userRepository to return an empty Optional when findById is called with userId
	        when(userrepository.findById(userId)).thenReturn(Optional.empty());

	        // When & Then
	        // Ensure that a SweetMartAPIException is thrown with the expected status and message
	        SweetMartAPIException exception = assertThrows(SweetMartAPIException.class, () -> {
	            userserviceimpl.showUser(userId);
	        });

	        assertNotNull(exception); // Ensure that the exception is not null
	        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus()); // Check if the status matches
	        assertEquals("user not found", exception.getMessage()); // Check if the message matches

	        // Verify that the userRepository's findById method was called once with the given userId
	        verify(userrepository, times(1)).findById(userId);
	    }
	    
	    
}

