package com.codealpha.attendance.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;


import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Login endpoint to authenticate the userimport org.slf4j.Logger;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestParam String username, 
                                              @RequestParam String password, 
                                              @RequestParam String role,
                                              HttpSession session) {

        // Call authenticate method from LoginServiceImpl
        String responseMessage = loginService.authenticate(username, password, role);

        // Check the response message and return the appropriate response
        if ("Login successful".equals(responseMessage)) {
            // If successful, set the user in the session
            User user = loginService.getUserDetails(username);
            session.setAttribute("user", user); // Store user in session

            // Return structured response with success message and user data
            return ResponseEntity.ok(new LoginResponse("Login successful", user));
        } else if ("User does not exist".equals(responseMessage)) {
            return ResponseEntity.status(404).body("User not found");
        } else if ("Invalid password".equals(responseMessage)) {
            return ResponseEntity.status(401).body("Invalid password");
        } else if ("Role mismatch".equals(responseMessage)) {
            return ResponseEntity.status(403).body("Role mismatch");
        } else {
            return ResponseEntity.status(400).body("An error occurred");
        }
    }

    // LoginResponse class to wrap the message and user data
    public static class LoginResponse {
        private String message;
        private User user;

        public LoginResponse(String message, User user) {
            this.message = message;
            this.user = user;
        }

        public String getMessage() {
            return message;
        }

        public User getUser() {
            return user;
        }
    }
    // Additional endpoint to get the user's role (can be useful for debugging or checking session)
    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(@RequestParam String username) {
        UserRole role = loginService.getUserRole(username);

        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // Endpoint to get session user details
    @GetMapping("/session")
    public ResponseEntity<?> getSessionUser(HttpSession session) {
        // Get the user object from session
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Return user details if user is present in session
            return ResponseEntity.ok(user);
        } else {
            // If no user is found in session, return an error
            return ResponseEntity.status(404).body("No user logged in");
        }
    }

    // Endpoint to display all session data (for debugging)
    @GetMapping("/display-session")
    public ResponseEntity<?> displaySessionData(HttpSession session) {
        // Fetch all session attributes
        java.util.Enumeration<String> sessionAttributes = session.getAttributeNames();
        StringBuilder sessionData = new StringBuilder();

        while (sessionAttributes.hasMoreElements()) {
            String attributeName = sessionAttributes.nextElement();
            sessionData.append(attributeName)
                       .append(": ")
                       .append(session.getAttribute(attributeName))
                       .append("\n");
        }

        return ResponseEntity.ok(sessionData.toString());
    }
}
