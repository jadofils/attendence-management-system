package com.codealpha.attendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // Base URL for all endpoints
public class PageController {

    @GetMapping("")
    public String showIndexPage() {
        return "index"; // Returns index.html
    }

    @GetMapping("/dashboard")
    public String showDashboardPage() {
        return "dashboard"; // Returns dashboard.html
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Returns register.html
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Returns login.html
    }

    @GetMapping("/forget")
    public String showForgetPage() {
        return "forget"; // Returns forget.html
    }
}
