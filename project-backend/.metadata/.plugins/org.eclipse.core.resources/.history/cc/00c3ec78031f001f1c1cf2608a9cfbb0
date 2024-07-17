package com.cg.onlinesweetmart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.onlinesweetmart.dto.UserResponseDto;
import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.service.UserService;

/**
 * Controller class for managing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Retrieves a list of all users.
     *
     * @return List of all users.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDto> showAllUsers() {
        return userService.showAllUsers();
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public User showUser(@PathVariable(value = "id") long userId) {
    	return userService.showUser(userId);
    }

    /**
     * Updates an existing user.
     *
     * @param user The user to be updated.
     * @return ResponseEntity with the updated user and HTTP status OK.
     * @throws SweetMartAPIException If the user to be updated is not found.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody User user) {
        try {
            UserResponseDto updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (SweetMartAPIException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Cancels a user by their ID.
     *
     * @param userId The ID of the user to be canceled.
     * @return ResponseEntity with a success message and HTTP status OK.
     * @throws SweetMartAPIException If the user to be canceled is not found.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelUser(@PathVariable(value = "id") long userId) {
        try {
            userService.cancelUser(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (SweetMartAPIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
