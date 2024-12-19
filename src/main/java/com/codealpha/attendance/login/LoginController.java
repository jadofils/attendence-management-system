package com.codealpha.attendance.login;

import com.codealpha.attendance.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            // Authenticate user
            User user = loginService.authenticate(username, password, role);

            // Store user data in the session
            session.setAttribute("user", user);
            session.setAttribute("role", user.getRole());
            session.setAttribute("username", user.getUsername());
            session.setMaxInactiveInterval(10800); // 3 hours timeout

            // Store success message in RedirectAttributes
            redirectAttributes.addFlashAttribute("message", "Login successful. Redirecting to dashboard...");
            return "redirect:/login"; // Redirect to the index page

        } catch (IllegalArgumentException e) {
            // Store error message in RedirectAttributes
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login"; // Redirect back to the login page
        }
    }

    @GetMapping("/clear-messages")
    public String clearMessages(HttpSession session) {
        // Clear session messages
        session.removeAttribute("message");
        session.removeAttribute("error");
        return "login"; // Return to the login page
    }
}
