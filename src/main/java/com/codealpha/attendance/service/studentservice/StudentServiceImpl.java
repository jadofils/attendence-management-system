package com.codealpha.attendance.service.studentservice;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.
Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.model.Student;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.repository.CourseRepository;
import com.codealpha.attendance.repository.ProgramRepository;
import com.codealpha.attendance.repository.StudentRepository;
import com.codealpha.attendance.repository.UserRepository;


@Service
public class StudentServiceImpl implements StudentService {

  @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgramRepository programRepository; // Ensure you have this repository

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private StudentDTO convertToDTO(Student student) {
        List<Long> courseIds = student.getCourses() != null 
            ? student.getCourses().stream()
                .map(Course::getCourseId)
                .collect(Collectors.toList()) 
            : List.of();

        Long programId = student.getProgram() != null 
            ? student.getProgram().getProgramId() 
            : null;
        
        Long userId = student.getUser() != null 
            ? student.getUser().getUserId() 
            : null;

        return new StudentDTO(
            student.getStudentId(),
            student.getFirstName(),
            student.getLastName(),
            student.getEmail(),
            student.getPhoneNumber(),
            student.getEnrollmentDate(),
            programId,
            userId,
            courseIds
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }



   @Override
    public StudentDTO saveStudent(StudentDTO studentDTO, Long userId) {
        // Check if the user exists in the database
        User user = userRepository.findById(userId).orElseThrow(() -> 
            new IllegalArgumentException("User with ID " + userId + " does not exist."));
        
        // Check if the program exists in the database
        Program program = programRepository.findById(studentDTO.getProgramId()).orElseThrow(() ->
            new IllegalArgumentException("Program with ID " + studentDTO.getProgramId() + " does not exist."));
        
        // Check if each course exists in the database (for a list of courses)
        List<Course> courses = courseRepository.findAllById(studentDTO.getCourseIds()).stream()
            .collect(Collectors.toList());

        // If there are any missing courses, throw an exception
        if (courses.size() != studentDTO.getCourseIds().size()) {
            throw new IllegalArgumentException("One or more Course IDs are invalid.");
        }

        // Create a new Student object
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setProgram(program); // Set the program for the student
        
        // Set the enrollment date to the current date if not already set
        if (studentDTO.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDate.now());  // or any default value
        } else {
            student.setEnrollmentDate(studentDTO.getEnrollmentDate());
        }

        student.setUser(user); // Set the user for the student
        student.setCourses(courses); // Set the courses for the student
        
        // Save the student and return the DTO
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }



    // In StudentService
@Override
@Transactional
public StudentDTO updateStudent(Long studentId, StudentDTO studentDTO) {
    // Check if the student exists
    Student existingStudent = studentRepository.findById(studentId).orElseThrow(() -> 
        new IllegalArgumentException("Student with ID " + studentId + " does not exist."));

    // Update fields if provided
    if (studentDTO.getFirstName() != null) {
        existingStudent.setFirstName(studentDTO.getFirstName());
    }
    if (studentDTO.getLastName() != null) {
        existingStudent.setLastName(studentDTO.getLastName());
    }
    if (studentDTO.getEmail() != null) {
        existingStudent.setEmail(studentDTO.getEmail());
    }
    if (studentDTO.getPhoneNumber() != null) {
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
    }

    // Check if a program ID is provided and update it
    if (studentDTO.getProgramId() != null) {
        Program program = programRepository.findById(studentDTO.getProgramId()).orElseThrow(() -> 
            new IllegalArgumentException("Program with ID " + studentDTO.getProgramId() + " does not exist."));
        existingStudent.setProgram(program);
    }

    // Check if course IDs are provided and update them
    if (studentDTO.getCourseIds() != null && !studentDTO.getCourseIds().isEmpty()) {
        List<Course> courses = courseRepository.findAllById(studentDTO.getCourseIds());
        if (courses.size() != studentDTO.getCourseIds().size()) {
            throw new IllegalArgumentException("Some courses with the provided IDs do not exist.");
        }
        existingStudent.setCourses(courses);
    }

    // Update enrollment date if provided
    if (studentDTO.getEnrollmentDate() != null) {
        existingStudent.setEnrollmentDate(studentDTO.getEnrollmentDate());
    }

    // Save the updated student
    Student savedStudent = studentRepository.save(existingStudent);

    // Convert to DTO and return
    return convertToDTO(savedStudent);
}


//.In StudentService
@Override
@Transactional
public void deleteStudent(Long studentId) {
    // Check if the student exists
    Student existingStudent = studentRepository.findById(studentId).orElseThrow(() -> 
        new IllegalArgumentException("Student with ID " + studentId + " does not exist."));

    // Remove the student
    studentRepository.delete(existingStudent);
}


}