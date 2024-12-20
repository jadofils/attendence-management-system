package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.UserRole;
import com.codealpha.attendance.service.userservice.UserService;

import com.codealpha.attendance.dto.UserDTO;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    private final String UPLOAD_DIR = "src/main/java/com/codealpha/attendance/uploads/";
    private final String UPLOADED_UPDATED_DIR = "src/main/java/com/codealpha/attendance/updatedUploads/";

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("studentProfile") MultipartFile studentProfile) {
    
        // Debug to ensure password is passed
        System.out.println("Received Password: " + password);
    
        if (username == null || username.trim().isEmpty()) {
            throw UserException.badRequest("Username is required and cannot be empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw UserException.badRequest("Password is required and cannot be empty.");
        }
    
        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw UserException.badRequest("Invalid role provided.");
        }
    
        String savedFileName = saveProfileImage(studentProfile, UPLOAD_DIR);
    
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(password) // Set password here
                .role(userRole)
                .studentProfile(savedFileName)
                .build();
    
        System.out.println("UserDTO: " + userDTO); // Debug DTO
        UserDTO savedUser = userService.saveUser(userDTO);
    
        return new ResponseEntity<>(new ApiResponse("User created successfully", savedUser), HttpStatus.CREATED);
    }

    // Fetch user details by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long userId,
            @RequestParam("username") String username,
            @RequestParam("role") String role,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "studentProfile", required = false) MultipartFile studentProfile) {

        if (username == null || username.trim().isEmpty()) {
            throw UserException.badRequest("Username is required and cannot be empty.");
        }

        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw UserException.badRequest("Invalid role. Please provide a valid role (e.g., USER, ADMIN).");
        }

        UserDTO existingUser = userService.getUserById(userId);
        existingUser.setUsername(username);
        existingUser.setRole(userRole);
        if (password != null && !password.isEmpty()) {
            existingUser.setPassword(password);
        }

        if (studentProfile != null && !studentProfile.isEmpty()) {
            validateProfileImage(studentProfile);
            String savedFileName = saveProfileImage(studentProfile, UPLOADED_UPDATED_DIR);
            existingUser.setStudentProfile(savedFileName);
        }

        UserDTO updatedUser = userService.updateUser(userId, existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        return new ResponseEntity<>(userService.countUsers(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role) {
        List<UserDTO> users = username != null ? userService.searchUsersByUsername(username)
                                               : userService.searchUsersByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @SuppressWarnings("null")
    private String saveProfileImage(MultipartFile profileImage, String uploadDir) {
        File uploadsDir = new File(uploadDir);
        if (!uploadsDir.exists()) uploadsDir.mkdirs();

        String fileName = "";
        try {
            String originalFileName = profileImage.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + fileExtension;
            Path targetPath = Path.of(uploadDir, fileName);
            Files.copy(profileImage.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ServiceException("Failed to save profile image.");
        }
        return fileName;
    }

    private void validateProfileImage(MultipartFile profileImage) {
        if (profileImage.isEmpty()) throw new ServiceException("Profile image cannot be empty.");
        if (profileImage.getSize() > 2 * 1024 * 1024) throw new ServiceException("File size exceeds 2MB limit.");
        String contentType = profileImage.getContentType();
        if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
            throw new ServiceException("Only JPEG and PNG formats are supported.");
        }
    }

    class ApiResponse {
        private String message;
        private Object data;
        public ApiResponse(String message, Object data) {
            this.message = message;
            this.data = data;
        }
        public String getMessage() { return message; }
        public Object getData() { return data; }
    }
}
