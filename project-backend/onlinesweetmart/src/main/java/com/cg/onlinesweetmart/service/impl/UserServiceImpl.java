package com.cg.onlinesweetmart.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cg.onlinesweetmart.dto.UserResponseDto;
import com.cg.onlinesweetmart.entity.User;
import com.cg.onlinesweetmart.exception.SweetMartAPIException;
import com.cg.onlinesweetmart.repository.UserRepository;
import com.cg.onlinesweetmart.service.UserService;

/**
 * Implementation of the UserService interface for managing users.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a list of all users.
     *
     * @return List of all users.
     */
    @Override
    public List<UserResponseDto> showAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
        			.map(user -> new UserResponseDto(user.getUserId(), user.getUsername(), user.getEmail(), user.getSweetOrder()))
        			.collect(Collectors.toList());
    }

    /**
     * Updates an existing user.
     *
     * @param user The user to be updated.
     * @return The updated user.
     * @throws SweetMartAPIException If the user to be updated is not found.
     */
    @Override
    public UserResponseDto updateUser(User user) {
        if (userRepository.existsById(user.getUserId())) {
            User updatedUser = userRepository.save(user);
            return new UserResponseDto(updatedUser.getUserId(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getSweetOrder());
        } else {
            throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "User not found with ID: " + user.getUserId());
        }
    }

    /**
     * Cancels a user by their ID.
     *
     * @param userId The ID of the user to be canceled.
     * @throws SweetMartAPIException If the user to be canceled is not found.
     */
    @Override
    public void cancelUser(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }
    }

	@Override
	public User showUser(long userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		if (!userOpt.isPresent()) {
			
			throw new SweetMartAPIException(HttpStatus.NOT_FOUND, "user not found");
		}
		
		User user = userOpt.get();
		return user;
		
	}

	@Override
	public User getUserByUsernameOrEmail(String usernameOrEmail) {
		return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).get();
	}
}