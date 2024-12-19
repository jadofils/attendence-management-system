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
    
    @GetMapping("/login")
    public String login() {
        return "login"; // Returns dashboard.html
    }
}
