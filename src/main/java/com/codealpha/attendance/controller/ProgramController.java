package com.codealpha.attendance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.service.programservice.ProgramService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/programs")
@AllArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    // Create a new program
    @PostMapping
    public ResponseEntity<Program> createProgram(@RequestBody Program program) {
        Program savedProgram = programService.saveProgram(program);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProgram);
    }

    // Retrieve all programs
    @GetMapping
    public ResponseEntity<List<Program>> getAllPrograms() {
        List<Program> programs = programService.getAllPrograms();
        return ResponseEntity.ok(programs);
    }

    // Update an existing program
    @PutMapping("/{programId}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long programId, @RequestBody Program updatedProgram) {
        try {
            Program updated = programService.updateProgram(programId, updatedProgram);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/program/{programId}")
    public ResponseEntity<Program> getProgram(@PathVariable Long programId) {
        Program program = programService.getProgramWithCourses(programId);
        return new ResponseEntity<>(program, HttpStatus.OK);
    }
    
    // Find a program by ID
    @GetMapping("/{id}")
    public ResponseEntity<Program> findProgramById(@PathVariable Long id) {
        Optional<Program> result = programService.findProgramById(id); // Pass only the ID
    
        // Checking if the result is present using Optional's method
        if (result.isEmpty()) {
            System.out.println("Program with ID " + id + " not found");
            return ResponseEntity.notFound().build();
        }
    
        // If the program is found, return it wrapped in ResponseEntity
        return ResponseEntity.ok(result.get());
    }
    

    // Delete a program by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProgram(@PathVariable Long id) {
        try {
            programService.deleteProgram(id); // Pass only the ID
            return ResponseEntity.ok("Program deleted successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Program not found: " + id);
        }
    }

    @GetMapping("/count")
public ResponseEntity<Long> countPrograms() {
    return ResponseEntity.ok(programService.countPrograms());
}
   // Search programs by programName or programId
   @GetMapping("/search")
   public List<Program> searchPrograms(@RequestParam String searchTerm) {
       return programService.searchPrograms(searchTerm);
   }

}
