package com.codealpha.attendance.service.programservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.codealpha.attendance.dto.ProgramDTO;
import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.repository.ProgramRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    @Override
    public ProgramDTO saveProgram(ProgramDTO programDTO) {
        // Convert DTO to Entity
        Program program = new Program();
        program.setProgramName(programDTO.getProgramName());
        program.setProgramDescription(programDTO.getProgramDescription());

        // Save entity and return DTO
        Program savedProgram = programRepository.save(program);
        return convertToDTO(savedProgram);
    }

    @Override
    public List<ProgramDTO> getAllPrograms() {
        return programRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProgramDTO findProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Program not found with ID: " + id));
        return convertToDTO(program);
    }

    @Override
    public ProgramDTO updateProgram(Long programId, ProgramDTO updatedProgramDTO) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found with ID: " + programId));

        program.setProgramName(updatedProgramDTO.getProgramName());
        program.setProgramDescription(updatedProgramDTO.getProgramDescription());

        return convertToDTO(programRepository.save(program));
    }

    @Override
    public void deleteProgram(Long id) {
        if (!programRepository.existsById(id)) {
            throw new RuntimeException("Program not found with ID: " + id);
        }
        programRepository.deleteById(id);
    }

    @Override
    public long countPrograms() {
        return programRepository.count();
    }

   private ProgramDTO convertToDTO(Program program) {
    ProgramDTO dto = new ProgramDTO();
    dto.setProgramId(program.getProgramId());
    dto.setProgramName(program.getProgramName());
    dto.setProgramDescription(program.getProgramDescription());

    // Set course names
    if (program.getCourses() != null) {
        dto.setCourseNames(program.getCourses().stream()
                .map(Course::getCourseName) // Ensure Course has this method
                .collect(Collectors.toList()));
    }

    // Set student full names (firstName + lastName)
    if (program.getStudents() != null) {
        dto.setStudentNames(program.getStudents().stream()
                .map(student -> student.getFirstName() + " " + student.getLastName()) // Combine names
                .collect(Collectors.toList()));
    }

    return dto;
}

}
