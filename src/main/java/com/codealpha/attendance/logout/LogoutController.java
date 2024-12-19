package com.codealpha.attendance.logout;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // Invalidate the session
        session.invalidate();

        // Add a logout success message
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");

        // Redirect to the login page
        return "redirect:/index";
    }
}
