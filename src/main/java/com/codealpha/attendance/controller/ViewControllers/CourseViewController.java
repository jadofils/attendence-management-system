package com.codealpha.attendance.controller.ViewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.codealpha.attendance.dto.CourseDTO;
import com.codealpha.attendance.dto.ProgramDTO;
import com.codealpha.attendance.service.courseService.CourseService;
import com.codealpha.attendance.service.programservice.ProgramService;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseViewController {

    private final CourseService courseService;
    private final ProgramService programService;

    // Constructor injection
    public CourseViewController(CourseService courseService, ProgramService programService) {
        this.courseService = courseService;
        this.programService = programService;
    }

    @GetMapping
    public String getAllCourses(Model model) {
        // Add an empty CourseDTO for form binding
        model.addAttribute("courseDTO", new CourseDTO());

        // Fetch all programs for the dropdown
        List<ProgramDTO> programs = programService.getAllPrograms();
        model.addAttribute("programs", programs);

        // Fetch courses from the service
        List<CourseDTO> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        // Render the courses/select.html template
        return "courses/select";
    }


    // Endpoint to display a specific course by ID
    @GetMapping("/{courseId}")
    public String getCourseById(@PathVariable Long courseId, Model model) {
        // Fetch the course by its ID
        CourseDTO course = courseService.getCourseById(courseId);

        // Add the course to the model
        model.addAttribute("course", course);

        // Fetch all programs for the dropdown (if necessary)
        List<ProgramDTO> programs = programService.getAllPrograms();
        model.addAttribute("programs", programs);

        // Render the course details template
        return "courses/select";
    }
}
