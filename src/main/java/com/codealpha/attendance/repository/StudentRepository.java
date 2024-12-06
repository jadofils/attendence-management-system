package com.codealpha.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.codealpha.attendance.model.Student;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    
    List<Student> findByProgramProgramId(Long programId);
    
    Optional<Student> findByStudentIdAndProgramProgramIdAndUserUserId(Long studentId, Long programId, Long userId);
    
    Optional<Student> findByProgramProgramIdAndUserUserId(Long programId, Long userId);
    
    @SuppressWarnings("null")
    Optional<Student> findById(Long studentId);
    
    @Query("SELECT SUM(s.program.programId) FROM Student s")
    Long getSumOfProgramIds();
    
    @Query("SELECT s.program.programId, COUNT(s) FROM Student s GROUP BY s.program.programId")
    List<Object[]> getSumGroupedByProgramId();
    
    // Search students by email using LIKE (this one is fine as email is String)
    List<Student> findByEmailContainingIgnoreCase(String email);
    
    // Modified to handle numeric studentId search
    @Query("SELECT s FROM Student s WHERE CAST(s.studentId AS string) LIKE CONCAT('%', :studentId, '%')")
    List<Student> findByStudentIdContaining(@Param("studentId") String studentId);
    
    // Modified to handle numeric programId search
    @Query("SELECT s FROM Student s WHERE CAST(s.program.programId AS string) LIKE CONCAT('%', :programId, '%')")
    List<Student> findByProgramIdContaining(@Param("programId") String programId);
    
    // Modified to handle numeric userId search
    @Query("SELECT s FROM Student s WHERE CAST(s.user.userId AS string) LIKE CONCAT('%', :userId, '%')")
    List<Student> findByUserIdContaining(@Param("userId") String userId);



    @SuppressWarnings("null")
    List<Student> findAll();
}