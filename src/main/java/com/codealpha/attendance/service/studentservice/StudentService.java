package com.codealpha.attendance.service.studentservice;

import java.util.List;

import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.model.Student;

public interface StudentService {
    List<StudentDTO> getAllStudents();

    Student getStudentById(Long studentId);
    StudentDTO getStudentDataById(Long studentId);

    StudentDTO saveStudent(StudentDTO studentDTO,Long userId);

    StudentDTO updateStudent(Long studentId, StudentDTO studentDTO);

    void deleteStudent(Long studentId);

  
}
