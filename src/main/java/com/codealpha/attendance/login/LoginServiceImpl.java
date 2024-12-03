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

        // Fetch user by username and role
        Optional<User> userOptional = userRepository.findByUsernameAndRole(username, userRole);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Match the plain password
            if (password.equals(user.getPassword())) {
                return user; // Authentication successful
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        } else {
            throw new IllegalArgumentException("User not found with username and role: " + username + ", " + role);
        }
    }
}
