package com.codealpha.attendance.controller;
import com.codealpha.attendance.dto.CourseDTO;
import com.codealpha.attendance.service.courseService.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")

public class CourseController {
    private final CourseService courseService;

    // Constructor injection
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @PostMapping("/{programId}")
    public ResponseEntity<Map<String, String>> createCourse(
            @PathVariable Long programId, 
            @RequestBody CourseDTO courseDTO) {
    
        Map<String, String> response = new HashMap<>();
    
        try {
            CourseDTO createdCourse = courseService.createCourse(courseDTO, programId);
            response.put("status", "success");
            response.put("message", "Course added successfully!");
            response.put("courseId", String.valueOf(createdCourse.getCourseId())); // Use String.valueOf() for better type safety
            return ResponseEntity.ok(response);
    
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("message", e.getMessage()); // Send error message if course exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
    
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        System.out.println("CourseDTO: " + courseDTO);
        System.out.println("CourseID: " + courseId);
        
        
        return ResponseEntity.ok(courseService.updateCourse(courseId, courseDTO));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long courseId) {
            System.out.println("CourseID: " + courseId);

        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully.");
    }
}
