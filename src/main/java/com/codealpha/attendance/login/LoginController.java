package com.codealpha.attendance.login;

import com.codealpha.attendance.model.User;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest, HttpSession session) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        String role = loginRequest.get("role");

        try {
            // Authenticate user
            User user = loginService.authenticate(username, password, role);

            // Store user data in session
            session.setAttribute("user", user);  // Store user object in session

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("user", user);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // Handle error response
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No user logged in");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();  // Invalidate session on logout
        return ResponseEntity.ok("Logout successful");
    }
}
