package com.codealpha.attendance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codealpha.attendance.model.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findByProgramName(String programName);
    @SuppressWarnings("null")
    Optional<Program> findById(Long id);
    long count();

    List<Program> findByProgramNameContainingIgnoreCase(String programName);

    // Fix: Ensure that you're using the correct field names
    List<Program> findByProgramNameContainingIgnoreCaseOrProgramId(String programName, Long programId);

    @Query("SELECT p FROM Program p LEFT JOIN FETCH p.courses WHERE p.programId = :programId")
Program findProgramWithCourses(@Param("programId") Long programId);

}
