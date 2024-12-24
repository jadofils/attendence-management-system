package com.codealpha.attendance.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codealpha.attendance.dto.ProgramDTO;
import com.codealpha.attendance.service.programservice.ProgramService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    
    @PostMapping
    public ResponseEntity<?> saveProgram(@RequestBody ProgramDTO programDTO) {
        System.out.println("Program Name: " + programDTO.getProgramName());
        System.out.println("Program Description: " + programDTO.getProgramDescription());
    
        // Simulate saving the program
        programService.saveProgram(programDTO);
    
        // Return a proper JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Program saved successfully");
        return ResponseEntity.ok(response);
    }
    
    

    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllPrograms() {
        try {
            List<ProgramDTO> programs = programService.getAllPrograms();
            return ResponseEntity.ok(programs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDTO> getProgramById(@PathVariable Long id) {
        try {
            ProgramDTO program = programService.findProgramById(id);
            if (program != null) {
                return ResponseEntity.ok(program);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramDTO> updateProgram(
            @PathVariable Long id, 
            @RequestBody ProgramDTO programDTO) {
        try {
            ProgramDTO updatedProgram = programService.updateProgram(id, programDTO);
            if (updatedProgram != null) {
                return ResponseEntity.ok(updatedProgram);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        try {
            programService.deleteProgram(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}