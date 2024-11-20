package com.codealpha.attendance.service.programservice;

import java.util.List;

import com.codealpha.attendance.model.Program;

public interface ProgramService {
    Program saveProgram(Program program);
    List<Program> getAllPrograms(); // Retrieve all programs
    public Program updateProgram(int programId, Program updatedProgram);


}
