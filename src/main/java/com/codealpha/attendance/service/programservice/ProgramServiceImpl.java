package com.codealpha.attendance.service.programservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.repository.ProgramRepository;

import jakarta.transaction.Transactional;
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


    @Transactional
    @Override
    public List<Program> getAllPrograms() {
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
        // Validate input ID
        if (id == null) {
            throw new IllegalArgumentException("Program ID must not be null");
        }
    
        // Attempt to find the program by ID
        Optional<Program> program = programRepository.findById(id);
        
        // If no program is found, you could return Optional.empty() or handle it here
        if (program.isEmpty()) {
            throw new RuntimeException("Program not found with ID: " + id);
        }
    
        return program;
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

    @Override
    public Program getProgramWithCourses(Long programId) {
        return programRepository.findProgramWithCourses(programId);
    }
    
}
