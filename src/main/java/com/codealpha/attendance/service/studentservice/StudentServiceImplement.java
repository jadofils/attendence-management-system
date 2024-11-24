package com.codealpha.attendance.service.studentservice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.model.Student;
import com.codealpha.attendance.repository.ProgramRepository;
import com.codealpha.attendance.repository.StudentRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImplement implements StudentService {

    private StudentRepository studentRepository;
    private ProgramRepository programRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Program saveProgram(Program program) {
        return programRepository.save(program);
    }

    // Method to fetch all students from the database
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    
  public Optional<Student> findByProgramAndUser(Long programId, Long userId) {
    return studentRepository.findByProgramProgramIdAndUserUserId(programId, userId);
}

  @Override
public Student findByStudentProgramAndUser(Long studentId, Long programId, Long userId) {
    return studentRepository.findByStudentIdAndProgramProgramIdAndUserUserId(studentId, programId, userId)
            .orElseThrow(() -> new IllegalArgumentException(
                    "Student not found for Student ID: " + studentId +
                    ", Program ID: " + programId +
                    ", and User ID: " + userId));
}


    @Override
    public Student updateStudent(Student updatedStudent) {
        Long studentId = updatedStudent.getStudentId(); // Assuming you have a `getStudentId` method
        Long programId = updatedStudent.getProgram().getProgramId();
        Long userId = updatedStudent.getUser().getUserId();
    
        // Find the existing student based on programId and userId
        Student existingStudent = studentRepository
                .findByStudentIdAndProgramProgramIdAndUserUserId(studentId, programId, userId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Student not found for Student ID: " + studentId +
                        ", Program ID: " + programId +
                        ", and User ID: " + userId));
    
        // Update only non-referenced fields
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
        existingStudent.setEnrollmentDate(updatedStudent.getEnrollmentDate());
    
        // Save and return the updated student
        return studentRepository.save(existingStudent);
    }

    @Override
    public Optional<Student> findStudentById(Long studentId) {
     return studentRepository.findById(studentId);
    }

   
    public void deleteStudentById(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        
        if (!student.isPresent()) {
            throw new IllegalArgumentException("Student not found for ID: " + studentId);
        }
    
        studentRepository.deleteById(studentId);
    }

    @Override
    public long count(Student Long) {
      return studentRepository.count();
    }

    // Implement the method to get sum of students grouped by programId
    @Override
    public List<Object[]> getSumGroupedByProgramId() {
        return studentRepository.getSumGroupedByProgramId();
    }

    @Override
    public List<Student> searchStudents(String email, String studentId, Long programId, Long userId) {
        if (email != null && !email.isEmpty()) {
            return studentRepository.findByEmailContainingIgnoreCase(email);
        } else if (studentId != null && !studentId.isEmpty()) {
            return studentRepository.findByStudentIdContaining(studentId);
        } else if (programId != null) {
            return studentRepository.findByProgramIdContaining(programId.toString());
        } else if (userId != null) {
            return studentRepository.findByUserIdContaining(userId.toString());
        } else {
            // If no parameters are provided, return an empty list or all students (based on requirements).
            return List.of();
        }
    }

    public Student getStudentById(Long studentid) {
        // Fetch the student by ID, and if not found, throw an exception or return null
        return studentRepository.findById(studentid)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + studentid));
    }
    

}
