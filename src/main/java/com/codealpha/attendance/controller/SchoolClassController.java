package com.codealpha.attendance.controller;

import com.codealpha.attendance.model.SchoolClass;

import com.codealpha.attendance.service.schoolService.SchoolClassService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {

    @Autowired
    private SchoolClassService schoolClassService;
    // Endpoint to get all classes
    @GetMapping
    public List<SchoolClass> getAllClasses() {
        return schoolClassService.getAllClasses();
    }

    @PostMapping("/{userId}/{courseId}")
public ResponseEntity<?> createClass(@PathVariable Long userId, @PathVariable Long courseId, 
                                     @RequestBody @Valid SchoolClass schoolClass) {
    SchoolClass createdClass = schoolClassService.createClass(userId, courseId, schoolClass);
    
    if (createdClass == null) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Failed to create the class. Please try again.");
    }
    
    return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
}


    @DeleteMapping("/{userId}/{classId}")
public ResponseEntity<String> deleteScheduledClass(@PathVariable Long userId, @PathVariable Long classId) {
    try {
        schoolClassService.deleteScheduled(userId, classId);
        return ResponseEntity.ok("Scheduled class successfully deleted.");
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

@PutMapping("/{userId}/{classId}")
    public ResponseEntity<SchoolClass> updateClass(
            @PathVariable Long userId,
            @PathVariable Long classId,
            @RequestBody SchoolClass updatedSchoolClass) {
        try {
            // Call the service method to update the class
            SchoolClass updatedClass = schoolClassService.updateClass(userId, classId, updatedSchoolClass);
            return ResponseEntity.ok(updatedClass); // Return the updated class
        } catch (RuntimeException ex) {
            // Handle exceptions and return meaningful error responses
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
//get mapping for the counting all classes scheduled 
@GetMapping("/count-classes")
public int count(){
    return (int) schoolClassService.count();
}
}
