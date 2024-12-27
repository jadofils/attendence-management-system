package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.service.schoolService.SchoolClassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/instructors") // Define the base URL for instructor-related endpoints
public class InstructorController {

    @Autowired
    private SchoolClassService schoolClassService;

    @GetMapping 
    public ResponseEntity<List<User>> getAllInstructors() {
        List<User> SchoolClass = schoolClassService.getAllInstructors();
        return new ResponseEntity<>(SchoolClass, HttpStatus.OK); 
    }
}