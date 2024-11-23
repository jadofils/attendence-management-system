package com.codealpha.attendance.service.userservice;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        // Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ServiceException("Username already exists");
        }
        validateProfileImage(user.getStudentProfile());
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

    @Override
    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        // Fetch existing user
        User existingUser = getUserById(userId);
        
        // Update fields
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setStudentProfile(updatedUser.getStudentProfile());
        
        validateProfileImage(existingUser.getStudentProfile());
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        // Verify user exists before deletion
        User existingUser = getUserById(userId);
        userRepository.delete(existingUser);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

   

    private void validateProfileImage(String profileImage) {
        if (profileImage == null || !profileImage.matches(".*\\.(jpeg|png|jpg|svg|tiff)$")) {
            throw new IllegalArgumentException(
                "Invalid profile image. Allowed extensions: jpeg, png, jpg, svg, tiff."
            );
        }
    }

    @Override
    public List<User> searchUsersByRole(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }
        // Trim spaces to avoid issues with search query
        keyword = keyword.trim();
        return userRepository.searchUsersByKeyword(keyword);
    }

    @Override
    public Object findById(Long userId) {
        return userRepository.findById(userId);
    }
    
}