package com.codealpha.attendance.controller;

import java.util.List;

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
    public ResponseEntity<ProgramDTO> createProgram(@RequestBody ProgramDTO programDTO) {
        ProgramDTO savedProgram = programService.saveProgram(programDTO);
        return ResponseEntity.ok(savedProgram);
    }

    @GetMapping
    public ResponseEntity<List<ProgramDTO>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramDTO> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.findProgramById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramDTO> updateProgram(
            @PathVariable Long id, @RequestBody ProgramDTO programDTO) {
        ProgramDTO updatedProgram = programService.updateProgram(id, programDTO);
        return ResponseEntity.ok(updatedProgram);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countPrograms() {
        return ResponseEntity.ok(programService.countPrograms());
    }
}
