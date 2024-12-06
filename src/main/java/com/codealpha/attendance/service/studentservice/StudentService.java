package com.codealpha.attendance.service.studentservice;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.model.Student;

public interface StudentService {

    // Method to save a student
    Student createStudent(Student student, Long userId, Long programId);

    // Method to save a program
    Program saveProgram(Program program);

   

    // Method to update a student's information
    Student updateStudent(Student updatedStudent);

    // Method to find a student by their ID
    Optional<Student> findStudentById(Long studentId);

    // Method to find a student by program and user IDs
    Optional<Student> findByProgramAndUser(Long programId, Long userId);

    // Method to find a student by student ID, program ID, and user ID
    Student findByStudentProgramAndUser(Long studentId, Long programId, Long userId);
    
    public void deleteStudentById(Long studentId) ;

    //count the students and all programs
    long count(Student Long);
    //count students grouped by id
    List<Object[]> getSumGroupedByProgramId();

    // Method to search students by different parameters
    List<Student> searchStudents(String email, String studentId, Long programId, Long userId);

    Student getStudentById(Long studentid);

    List<Student> findAll();

}
