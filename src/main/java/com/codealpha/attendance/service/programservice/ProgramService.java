package com.codealpha.attendance.service.programservice;

import java.util.List;
import com.codealpha.attendance.dto.ProgramDTO;

public interface ProgramService {
    ProgramDTO saveProgram(ProgramDTO programDTO);
    List<ProgramDTO> getAllPrograms();
    ProgramDTO findProgramById(Long id);
    ProgramDTO updateProgram(Long programId, ProgramDTO updatedProgramDTO);
    void deleteProgram(Long id);
    long countPrograms(); // Count total programs
    boolean existsById(Long programId);
}
