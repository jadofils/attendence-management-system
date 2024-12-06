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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    // Directory for initial sign-up profile images
    private String UPLOAD_DIR = "src/main/java/com/codealpha/attendance/uploads/";

    // Directory for updated profile images
    private String UPLOADED_UPDATED_DIR = "src/main/java/com/codealpha/attendance/updatedUploads/";

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

        // Save the profile image to the initial uploads directory
        String savedFileName = saveInitialProfileImage(studentProfile);

        User user = new User();
        System.out.println(user);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(userRole);
        user.setStudentProfile(savedFileName);  // Store the file name in the database

        User savedUser = userService.saveUser(user);

        return new ResponseEntity<>(
                new ApiResponse("User created successfully", savedUser), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userId,
            @RequestParam("username") String username,
            @RequestParam("role") String role,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "studentProfile", required = false) MultipartFile studentProfile) {
    
        System.out.println("Updating User ID: " + userId);
    
        if (username == null || username.trim().isEmpty()) {
            throw UserException.badRequest("Username is required and cannot be empty.");
        }
    
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw UserException.badRequest("Invalid role. Please provide a valid role (e.g., USER, ADMIN).");
        }
    
        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            throw UserException.notFound("User with ID " + userId + " not found.");
        }
    
        existingUser.setUsername(username);
        existingUser.setRole(userRole);
    
        if (password != null && !password.isEmpty()) {
            existingUser.setPassword(password);
        }
    
        if (studentProfile != null && !studentProfile.isEmpty()) {
            try {
                validateProfileImage(studentProfile);
                String savedFileName = saveUpdatedProfileImage(studentProfile, existingUser.getStudentProfile());
                existingUser.setStudentProfile(savedFileName); // Update the profile image
            } catch (ServiceException e) {
                throw UserException.badRequest(e.getMessage());
            }
        }
    
        User updatedUser = userService.updateUser(userId, existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    private String saveInitialProfileImage(MultipartFile profileImage) {
        // Ensure the uploads directory exists
        File uploadsDir = new File(UPLOAD_DIR);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }

        // Generate a unique file name to prevent overwriting
        String originalFileName = profileImage.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = "initial_" + UUID.randomUUID().toString() + fileExtension;
        Path targetPath = Path.of(UPLOAD_DIR, uniqueFileName);

        try {
            // Copy the file to the target path
            Files.copy(profileImage.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ServiceException("Failed to save initial profile image.");
        }

        return uniqueFileName;
    }

    private String saveUpdatedProfileImage(MultipartFile profileImage, String existingFileName) {
        // Ensure the updated uploads directory exists
        File updatedUploadsDir = new File(UPLOADED_UPDATED_DIR);
        if (!updatedUploadsDir.exists()) {
            updatedUploadsDir.mkdirs();
        }

        // Generate a unique file name to prevent overwriting
        String originalFileName = profileImage.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = "updated_" + UUID.randomUUID().toString() + fileExtension;
        Path targetPath = Path.of(UPLOADED_UPDATED_DIR, uniqueFileName);

        try {
            // Copy the file to the target path
            Files.copy(profileImage.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Delete the existing file if it exists
            if (existingFileName != null && !existingFileName.isEmpty()) {
                // Check in both original and updated uploads directories
                Path existingFilePathOriginal = Path.of(UPLOAD_DIR, existingFileName);
                Path existingFilePathUpdated = Path.of(UPLOADED_UPDATED_DIR, existingFileName);
                
                Files.deleteIfExists(existingFilePathOriginal);
                Files.deleteIfExists(existingFilePathUpdated);
            }
        } catch (IOException e) {
            throw new ServiceException("Failed to save or delete profile image.");
        }

        return uniqueFileName;
    }

    private void validateProfileImage(MultipartFile profileImage) {
        // Ensure the file is not empty
        if (profileImage.isEmpty()) {
            throw new ServiceException("Profile image cannot be empty.");
        }
    
        // Validate file size (e.g., limit to 2 MB)
        long maxFileSize = 2 * 1024 * 1024; // 2 MB in bytes
        if (profileImage.getSize() > maxFileSize) {
            throw new ServiceException("Profile image size exceeds the limit of 2 MB.");
        }
    
        // Validate file type (e.g., only accept JPEG and PNG)
        String contentType = profileImage.getContentType();
        if (!"image/jpeg".equalsIgnoreCase(contentType) && !"image/png".equalsIgnoreCase(contentType)) {
            throw new ServiceException("Only JPEG and PNG image formats are supported.");
        }
    
        // Validate file name to prevent invalid or malicious input
        String fileName = profileImage.getOriginalFilename();
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new ServiceException("Profile image file name is invalid.");
        }
    
        // Optionally, restrict the file extension
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if (!fileExtension.equals("jpg") && !fileExtension.equals("jpeg") && !fileExtension.equals("png")) {
            throw new ServiceException("Only .jpg, .jpeg, and .png file extensions are allowed.");
        }
    }
    


       // Get all users
       @GetMapping
       public ResponseEntity<List<User>> getAllUsers() {
           List<User> users = userService.getAllUsers();
           return new ResponseEntity<>(users, HttpStatus.OK);
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