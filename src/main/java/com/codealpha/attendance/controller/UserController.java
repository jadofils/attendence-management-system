package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.User;
import com.codealpha.attendance.service.userservice.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(
        @PathVariable Long userId, 
        @RequestBody User updatedUser
    ) {
        User user = userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.countUsers();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

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