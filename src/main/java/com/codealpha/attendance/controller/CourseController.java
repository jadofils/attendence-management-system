package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.service.courseService.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

   @PostMapping("/{programId}")
public ResponseEntity<Course> createCourse(@RequestBody Course course, @PathVariable Long programId) {
    Course createdCourse = courseService.createCourse(course, programId);
    return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
}


    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse) {
        return ResponseEntity.ok(courseService.updateCourse(courseId, updatedCourse));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok("Course deleted successfully.");
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @GetMapping("/name")
    public ResponseEntity<Course> getCourseByName(@RequestParam String courseName) {
        return ResponseEntity.ok(courseService.getCourseByName(courseName));
    }


    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
