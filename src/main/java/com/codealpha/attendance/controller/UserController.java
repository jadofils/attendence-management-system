package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;
import com.codealpha.attendance.service.userservice.UserService;
import com.codealpha.attendance.exception.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // Create user
    @PostMapping
    public ResponseEntity<?> createUser(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("role") String role,
        @RequestParam("studentProfile") MultipartFile studentProfile) {

        // Validate username
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Username is required and cannot be empty.");
        }

        // Validate password (e.g., length, strength)
        if (password == null || password.length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Password must be at least 8 characters long.");
        }

        // Convert role to UserRole enum and validate
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Invalid role. Please provide a valid role (e.g., USER, ADMIN).");
        }

        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(userRole);

        // Validate and set profile image
        try {
            validateProfileImage(studentProfile);
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage()); // Return specific error message
        }

        user.setStudentProfile(studentProfile.getOriginalFilename()); // Save only the filename or path

        // Save user using service
        User savedUser = userService.saveUser(user);

        return new ResponseEntity<>(
            new ApiResponse("User created successfully", savedUser), HttpStatus.CREATED);
    }

    // Validate profile image
    public void validateProfileImage(MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            throw new ServiceException("Profile image is required.");
        }

        // Check file size (100MB max size)
        long fileSizeInBytes = profileImage.getSize();
        long maxSizeInBytes = 100 * 1024 * 1024; // 100 MB
        if (fileSizeInBytes > maxSizeInBytes) {
            throw new ServiceException("Profile image must not exceed 100MB.");
        }

        // Check file extension
        String fileName = profileImage.getOriginalFilename();
        if (fileName != null) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            List<String> allowedExtensions = Arrays.asList("jpeg", "png", "jpg", "svg", "tiff");

            if (!allowedExtensions.contains(extension)) {
                throw new ServiceException("Invalid profile image. Allowed extensions: jpeg, png, jpg, svg, tiff.");
            }
        } else {
            throw new ServiceException("Invalid file name.");
        }
    }



    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update user
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
        @PathVariable Long userId, 
        @RequestBody User updatedUser
    ) {
        User user = userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get user count
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.countUsers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // Search users by username or role
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
        @RequestParam(required = false) String username,
        @RequestParam(required = false) String keyword
    ) {
        List<User> users;
        if (username != null) {
            users = userService.searchUsersByUsername(username);
        } else if (keyword != null) {
            users = userService.searchUsersByRole(keyword);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Search users by role with error handling
    @GetMapping("/search-by-keyword")
    public ResponseEntity<?> searchUsers(@RequestParam String keyword) {
        try {
            // Call the service to search users by role
            List<User> users = userService.searchUsersByRole(keyword.toUpperCase());
    
            // If users are found, return them in the response
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("message", "No users found for the role: " + keyword));
            }
            
            return ResponseEntity.ok(users);
            
        } catch (IllegalArgumentException e) {
            // Return a bad request response with a more meaningful error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Invalid role: " + keyword));
        } catch (Exception e) {
            // Return a general error response for unexpected issues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An unexpected error occurred"));
        }
    }
}
class ApiResponse {
    private String message;
    private Object data;

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}