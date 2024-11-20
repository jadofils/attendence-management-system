package com.codealpha.attendance.service.programservice;

import java.util.List;

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
        return programRepository.save(program);
    }

    @Override
    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    @Override
    public Program updateProgram(int programId, Program updatedProgram) {
        // Find the existing program by ID
        Program existingProgram = programRepository.findById((long) programId)
                                                   .orElseThrow(() -> new RuntimeException("Program not found"));
    
        // Update the existing program's fields with the new program's details
        existingProgram.setProgramName(updatedProgram.getProgramName());
        existingProgram.setProgramDescription(updatedProgram.getProgramDescription());
        
        // Save the updated program back to the repository
        return programRepository.save(existingProgram);
    }
    
}
