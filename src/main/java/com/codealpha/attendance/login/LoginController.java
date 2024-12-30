package com.codealpha.attendance.login;

import com.codealpha.attendance.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Enumeration;

@Controller
@RequestMapping("/api/user")
@CrossOrigin
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    @PostMapping("/login")
    public RedirectView login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Authenticate user
            User user = loginService.authenticate(username, password, role);
            System.out.println(user);
            // Save user in session
            session.setAttribute("user", user); // Store the entire User object

            // Session timeout (3 hours)
            session.setMaxInactiveInterval(10800); // 3 hours timeout
            
            // Store success message
            redirectAttributes.addFlashAttribute("message", "Login successful! Redirecting to dashboard...");
            
            // Redirect to dashboard
            return new RedirectView("/dashboard", true);
            
        } catch (IllegalArgumentException e) {
            // Store error message
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new RedirectView("/login", true); // Stay on login page
        }
    }
    
    // Clear session and logout
    @GetMapping("/logout")
    public RedirectView logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // Invalidate the session
        session.invalidate();
        
        // Add logout success message
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        
        // Redirect to login page
        return new RedirectView("/login", true);
    }

    // Clear error/success messages
    @GetMapping("/clear-messages")
    public RedirectView clearMessages(HttpSession session) {
        session.removeAttribute("message");
        session.removeAttribute("error");
        return new RedirectView("/login", true);
    }

    // Display session information
    @GetMapping("/session-info")
    @ResponseBody
    public String displaySessionInfo(HttpSession session) {
        StringBuilder sessionInfo = new StringBuilder();

        sessionInfo.append("Session ID: ").append(session.getId()).append("\n");
        sessionInfo.append("Creation Time: ").append(session.getCreationTime()).append("\n");
        sessionInfo.append("Last Accessed Time: ").append(session.getLastAccessedTime()).append("\n");
        sessionInfo.append("Max Inactive Interval: ").append(session.getMaxInactiveInterval()).append(" seconds\n");
        
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            sessionInfo.append("Attribute Name: ").append(attributeName).append(", Attribute Value: ").append(attributeValue).append("\n");
        }
        
        return sessionInfo.toString();
    }
}
