package com.codealpha.attendance.controller;

import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.service.studentservice.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.getStudentDataById(id);
        return ResponseEntity.ok(studentDTO);
    }
    @GetMapping("/all")
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<StudentDTO> createStudent(
            @RequestBody StudentDTO studentDTO, 
            @PathVariable Long userId) {
        // Call service method with studentDTO and userId
        StudentDTO savedStudent = studentService.saveStudent(studentDTO, userId);
        System.out.println(savedStudent+" "+userId);
        return ResponseEntity.ok(savedStudent);
    }

    
    @PutMapping("/update/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentDTO studentDTO) {
        // Call the service layer to update the student
        StudentDTO updatedStudent = studentService.updateStudent(studentId, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }




// In StudentController
@DeleteMapping("/delete/{studentId}")
public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
    // Call the service layer to delete the student
    studentService.deleteStudent(studentId);
    return ResponseEntity.ok("Student deleted successfully.");
}
}