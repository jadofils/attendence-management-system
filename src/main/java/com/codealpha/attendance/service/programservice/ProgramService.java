package com.codealpha.attendance.service.programservice;

import java.util.List;
import java.util.Optional;

import com.codealpha.attendance.model.Program;

public interface ProgramService {

    // Save a new program
    Program saveProgram(Program program);

    // Retrieve all programs
    List<Program> getAllPrograms();

    // Update an existing program by its ID
    Program updateProgram(Long programId, Program updatedProgram);

    // Find a program by its ID
    Optional<Program> findProgramById(Long id);

    // Delete a program by its ID
    void deleteProgram(Long id);

    // Count the total number of programs
    long countPrograms();

    // Search for programs by name or ID
    List<Program> searchPrograms(String searchTerm);

    Program getProgramWithCourses(Long programId);
}
