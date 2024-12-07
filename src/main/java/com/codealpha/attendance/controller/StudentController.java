package com.codealpha.attendance.controller;

import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.service.studentservice.StudentService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/all")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }
}

