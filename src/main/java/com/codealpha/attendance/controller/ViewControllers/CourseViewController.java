package com.codealpha.attendance.controller.ViewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;

import com.codealpha.attendance.dto.CourseDTO;
import com.codealpha.attendance.dto.ProgramDTO;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseViewController {

    private final RestTemplate restTemplate;

    // Constructor injection
    public CourseViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getAllCourses(Model model) {
        // Add an empty CourseDTO for form binding
        model.addAttribute("courseDTO", new CourseDTO());

        // Fetch all programs for the dropdown
        String programApiUrl = "http://localhost:8080/api/programs";
        ResponseEntity<List<ProgramDTO>> programResponse = restTemplate.exchange(
                programApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProgramDTO>>() {}
        );
        List<ProgramDTO> programs = programResponse.getBody();
        model.addAttribute("programs", programs);

        // Fetch courses from API
        String courseApiUrl = "http://localhost:8080/api/courses";
        ResponseEntity<List<CourseDTO>> courseResponse = restTemplate.exchange(
                courseApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CourseDTO>>() {}
        );
        List<CourseDTO> courses = courseResponse.getBody();

        // Add courses to model
        model.addAttribute("courses", courses);

        // Render the courses/select.html template
        return "courses/select";
    }


}