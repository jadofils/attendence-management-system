package com.codealpha.attendance.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.service.programservice.ProgramService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/programs")
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    // POST mapping to create a new program
    @PostMapping
    public ResponseEntity<Object> createProgram(@RequestBody Program program) {
        // Save the program using the service
        Program savedProgram = programService.saveProgram(program);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProgram);
    }

    // GET mapping to retrieve all programs
    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
        // Fetch all programs using the service
        List<Program> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }
    @SuppressWarnings("null")
    @PutMapping("/{programId}")
    public ResponseEntity<Program> updateProgram(@PathVariable int programId, @RequestBody Program updatedProgram) {
        try {
            // Call the service to update the program
            Program updated = programService.updateProgram(programId, updatedProgram);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            // If program is not found, return a 404 error
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
