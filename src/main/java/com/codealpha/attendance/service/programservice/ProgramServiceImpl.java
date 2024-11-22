package com.codealpha.attendance.service.programservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.repository.ProgramRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    @Override
    public Program saveProgram(Program program) {
        // Save the program and return the saved entity
        return programRepository.save(program);
    }

    @Override
    public List<Program> getAllPrograms() {
        // Retrieve and return all programs from the repository
        return programRepository.findAll();
    }

    @Override
    public Program updateProgram(Long programId, Program updatedProgram) {
        // Check if the program exists in the repository
        Optional<Program> existingProgram = programRepository.findById(programId);
        if (existingProgram.isPresent()) {
            Program program = existingProgram.get();
            // Update program fields as necessary
            program.setProgramName(updatedProgram.getProgramName());
            program.setProgramDescription(updatedProgram.getProgramDescription());
            // Save and return the updated program
            return programRepository.save(program);
        } else {
            throw new RuntimeException("Program not found with ID: " + programId);
        }
    }

    @Override
    public Optional<Program> findProgramById(Long id) {
        // Find and return the program by its ID
        return programRepository.findById(id);
    }

    @Override
    public void deleteProgram(Long id) {
        // Check if the program exists before deleting
        Optional<Program> program = programRepository.findById(id);
        if (program.isPresent()) {
            programRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Program not found with ID: " + id);
        }
    }

    @Override
    public long countPrograms() {
        // Count the total number of programs and return
        return programRepository.count();
    }

    @Override
    public List<Program> searchPrograms(String searchTerm) {
        try {
            // Try to parse the searchTerm as an ID (Long)
            Long programId = Long.parseLong(searchTerm);
            return programRepository.findByProgramNameContainingIgnoreCaseOrProgramId(searchTerm, programId);
        } catch (NumberFormatException e) {
            // If it's not a number, search by program name
            return programRepository.findByProgramNameContainingIgnoreCase(searchTerm);
        }
    }
    
}
