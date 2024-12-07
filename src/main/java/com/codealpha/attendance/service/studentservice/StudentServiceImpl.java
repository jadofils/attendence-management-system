package com.codealpha.attendance.service.studentservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.model.Student;
import com.codealpha.attendance.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Fetch all students with their program, courses, and attendance
    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAllStudentsWithProgramAndCoursesAndAttendance();
        return students.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    private StudentDTO convertToDTO(Student student) {
        // Extract course IDs from the student's courses
        List<Long> courseIds = student.getCourses().stream()
                                      .map(course -> course.getCourseId())
                                      .collect(Collectors.toList());
    
        // Safely extract Program ID and User ID, checking for null
        Long programId = student.getProgram() != null ? student.getProgram().getProgramId() : null;
        Long userId = student.getUser() != null ? student.getUser().getUserId() : null;
    
        // Map Student entity to StudentDTO
        return new StudentDTO(
            student.getStudentId(),
            student.getFirstName(),
            student.getLastName(),
            student.getEmail(),
            student.getPhoneNumber(),
            student.getEnrollmentDate(),
            programId,  // Safely passed Program ID
            userId,     // Safely passed User ID
            courseIds
        );
    }
    

    @Override
    public Student getStudentById(Long studentid) {
        // Implement the method to fetch student by ID
        return studentRepository.findById(studentid).orElseThrow(() -> new RuntimeException("Student not found"));
    }
}
