package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.User;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
public class SessionController {

    // **1. Get User Session**
    @GetMapping("/getUserSession")
    public ResponseEntity<?> getSessionUser(HttpSession session) {
        // Retrieve the user object from the session
        User sessionUser = (User) session.getAttribute("user");

        // Check if user exists in the session
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user found in session. Please log in first.");
        }

        // Return the user details
        return ResponseEntity.ok(sessionUser);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // Invalidate the session
        session.invalidate();

        // Add a logout success message
        redirectAttributes.addFlashAttribute("logoutMessage", "You have been logged out successfully.");

        // Redirect to the login page
        return "redirect:/login";
    }
}  
