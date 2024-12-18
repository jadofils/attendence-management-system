package com.codealpha.attendance.login;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;
import com.codealpha.attendance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User authenticate(String username, String password, String role) {
        UserRole userRole;
    
        // Convert the role string to UserRole enum
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role provided: " + role);
        }
    
        // Trim input username
        String trimmedUsername = username.trim();
    
        // Check if the username exists
        Optional<User> userByUsername = userRepository.findByUsername(trimmedUsername);
        if (userByUsername.isEmpty()) {
            throw new IllegalArgumentException("Username does not exist: " + trimmedUsername);
        }
    
        // Check if the role matches for the username
        Optional<User> userOptional = userRepository.findByUsernameAndRole(trimmedUsername, userRole);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("The provided role does not match for the username: " + trimmedUsername);
        }
    
        // Validate the password
        User user = userOptional.get();
        if (!password.equals(user.getPassword().trim())) { // Trim password comparison as well
            throw new IllegalArgumentException("Password is incorrect for the username and role provided.");
        }
    
        // Successful authentication
        return user;
    }
    
}
