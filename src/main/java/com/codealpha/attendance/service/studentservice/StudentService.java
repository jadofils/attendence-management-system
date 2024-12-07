package com.codealpha.attendance.service.studentservice;

import java.util.List;
import java.util.Optional;

import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.model.Student;

public interface StudentService {
    List<StudentDTO> getAllStudents();

    Student getStudentById(Long studentid);

  
}
