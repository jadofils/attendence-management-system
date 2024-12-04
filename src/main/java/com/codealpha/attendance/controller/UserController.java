package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;
import com.codealpha.attendance.service.userservice.UserService;
import com.codealpha.attendance.exception.ServiceException;
import com.codealpha.attendance.exception.UserException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

   
    private static final String UPLOAD_DIR = "uploads/";

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("studentProfile") MultipartFile studentProfile) {
        
        System.out.println("Username: " + username);

        if (username == null || username.trim().isEmpty()) {
            throw UserException.badRequest("Username is required and cannot be empty.");
        }

        if (password == null || password.length() < 8) {
            throw UserException.badRequest("Password must be at least 8 characters long.");
        }

        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw UserException.badRequest("Invalid role. Please provide a valid role (e.g., USER, ADMIN).");
        }

        try {
            validateProfileImage(studentProfile);
        } catch (ServiceException e) {
            throw UserException.badRequest(e.getMessage());
        }

        // Save the profile image to the uploads directory
        String savedFileName = saveProfileImage(studentProfile);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(userRole);
        user.setStudentProfile(savedFileName);  // Store the file name in the database

        User savedUser = userService.saveUser(user);

        return new ResponseEntity<>(
                new ApiResponse("User created successfully", savedUser), HttpStatus.CREATED);
    }

    private void validateProfileImage(MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            throw new ServiceException("Profile image is required.");
        }

        long fileSizeInBytes = profileImage.getSize();
        long maxSizeInBytes = 1000 * 1024 * 1024; // 100 MB
        if (fileSizeInBytes > maxSizeInBytes) {
            throw new ServiceException("Profile image must not exceed 100MB.");
        }

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

    private String saveProfileImage(MultipartFile profileImage) {
        // Ensure the uploads directory exists
        File uploadsDir = new File(UPLOAD_DIR);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }

        // Get the file name and create a path for the uploaded file
        String fileName = profileImage.getOriginalFilename();
        Path targetPath = Path.of(UPLOAD_DIR, fileName);

        try {
            // Copy the file to the target path
            Files.copy(profileImage.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ServiceException("Failed to save profile image.");
        }

        return fileName; // Return the file name to store in the database
    }
    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by ID
   @PutMapping("/{userId}")
public ResponseEntity<User> updateUser(
        @PathVariable Long userId,
        @RequestParam("username") String username,
        @RequestParam("role") UserRole role,
        @RequestParam(value = "password", required = false) String password,
        @RequestParam(value = "studentProfile", required = false) MultipartFile profile) {

    // Create an updated user object
    User updatedUser = new User();
    updatedUser.setUsername(username);
    updatedUser.setRole(role);

    // Optionally update the password
    if (password != null && !password.isEmpty()) {
        updatedUser.setPassword(password);
    }

    // If a profile image is provided, validate its extension
    if (profile != null && !profile.isEmpty()) {
        String fileExtension = getFileExtension(profile.getOriginalFilename());
        if (!isValidImageExtension(fileExtension)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Return 400 if the file extension is not valid
        }
        
        // Handle the file saving logic here if valid
        // For example: String profilePath = fileStorageService.storeFile(profile);
        // updatedUser.setProfilePath(profilePath);
    }

    // Update the user
    User user = userService.updateUser(userId, updatedUser);
    return new ResponseEntity<>(user, HttpStatus.OK);
}

// Helper method to get the file extension
private String getFileExtension(String filename) {
    int lastIndexOfDot = filename.lastIndexOf('.');
    return (lastIndexOfDot == -1) ? "" : filename.substring(lastIndexOfDot + 1).toLowerCase();
}

// Helper method to check if the file extension is valid
private boolean isValidImageExtension(String extension) {
    return List.of("jpeg", "png", "jpg", "svg", "tiff").contains(extension);
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
            @RequestParam(required = false) String keyword) {
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