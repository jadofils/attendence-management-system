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
}
