package com.codealpha.attendance.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;

@CrossOrigin(origins = "http://localhost:5000", allowCredentials = "true")
@RestController
@RequestMapping("/api/user/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
 private UserRole userRole;
    @PostMapping
    public ResponseEntity<?> authenticateUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        
        // Check if user exists
        User user = loginService.getUserDetails(username);
        System.out.println(user);
        if (user == null) {
            return ResponseEntity.status(404).body("User does not exist");
        }

        // Validate password
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        // Validate role
        if (!user.getRole().equals(com.codealpha.attendance.model.UserRole.valueOf(role))) {
            System.out.println(userRole);
            return ResponseEntity.status(403).body("Role mismatch");
        }

        // Login successful
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(@RequestParam String username) {
        User user = loginService.getUserDetails(username);
        if (user != null) {
            return ResponseEntity.ok(user.getRole());
        }
        return ResponseEntity.status(404).body("User not found");
    }
}
