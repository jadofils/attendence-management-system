package com.codealpha.attendance.dashboard;

import com.codealpha.attendance.model.User;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        // Retrieve user from session
        User user = (User) session.getAttribute("user");

        // Log session details
        logger.info("Session ID: {}", session.getId());
        logger.info("Session Creation Time: {}", session.getCreationTime());
        logger.info("Session Last Accessed Time: {}", session.getLastAccessedTime());
        logger.info("Session Max Inactive Interval: {} seconds", session.getMaxInactiveInterval());

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            // Log user details if session is available
            logger.info("Logged In User: Username: {}, Role: {}", user.getUsername(), user.getRole());
            
            // Add user details to response
            response.put("message", "User is still logged in");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            // If no user is found in session
            logger.info("No user is currently logged in.");
            response.put("message", "No user is logged in");
            return ResponseEntity.status(401).body(response);
        }
    }
}
