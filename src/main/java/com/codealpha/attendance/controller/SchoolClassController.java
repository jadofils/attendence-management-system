package com.codealpha.attendance.controller;

import com.codealpha.attendance.dto.SchoolClassDTO;
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

    // Endpoint to create a class
    @PostMapping("/{userId}")
    public ResponseEntity<?> createClass(@PathVariable Long userId,
                                         @RequestBody @Valid SchoolClassDTO schoolClassDTO) {
        try {
            SchoolClass createdClass = schoolClassService.createClass(userId, schoolClassDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to delete a scheduled class
    @DeleteMapping("/{userId}/{classId}")
    public ResponseEntity<String> deleteScheduledClass(@PathVariable Long userId, @PathVariable Long classId) {
        try {
            schoolClassService.deleteScheduled(userId, classId);
            return ResponseEntity.ok("Scheduled class successfully deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to update a class
    @PutMapping("/{userId}/{classId}")
    public ResponseEntity<?> updateClass(@PathVariable Long userId,
                                         @PathVariable Long classId,
                                         @RequestBody @Valid SchoolClassDTO schoolClassDTO) {
        try {
            SchoolClass updatedClass = schoolClassService.updateClass(userId, classId, schoolClassDTO);
            return ResponseEntity.ok(updatedClass);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to count all scheduled classes
    @GetMapping("/count-classes")
    public long count() {
        return schoolClassService.count();
    }

    // Endpoint to get a class by ID
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable Long classId) {
        try {
            SchoolClass schoolClass = schoolClassService.getClassById(classId);
            return ResponseEntity.ok(schoolClass);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
