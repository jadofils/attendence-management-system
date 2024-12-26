package com.codealpha.attendance.controller.ViewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.codealpha.attendance.dto.CourseDTO;
import com.codealpha.attendance.dto.ProgramDTO;
import com.codealpha.attendance.service.courseService.CourseService;
import com.codealpha.attendance.service.programservice.ProgramService;

import jakarta.validation.Valid;

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
        model.addAttribute("courseDTO", new CourseDTO()); // For form binding
        List<ProgramDTO> programs = programService.getAllPrograms(); 
        model.addAttribute("programs", programs);
        List<CourseDTO> courses = courseService.getAllCourses(); 
        model.addAttribute("courses", courses);
        return "courses/select"; 
    }

   @PostMapping("/add")
public String addCourse(
        @RequestParam("programId") Long programId,
        @Valid @ModelAttribute("courseDTO") CourseDTO courseDTO,
        BindingResult result,
        Model model) {

    // Validation error handling
    if (result.hasErrors()) {
        model.addAttribute("errorMessage", "Validation failed. Please correct the errors and try again.");
        model.addAttribute("programs", programService.getAllPrograms());
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses/select";
    }

    try {
        // Save the course using the service
        courseService.createCourse(courseDTO, programId);
        model.addAttribute("successMessage", "Course added successfully!");

    } catch (IllegalArgumentException e) {
        model.addAttribute("errorMessage", e.getMessage());
    }

    // Reload dropdowns and courses
    model.addAttribute("courseDTO", new CourseDTO());
    model.addAttribute("programs", programService.getAllPrograms());
    model.addAttribute("courses", courseService.getAllCourses());

    return "courses/select";
}


    
}