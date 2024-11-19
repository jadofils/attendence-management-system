package com.codealpha.attendance.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.model.Student;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.repository.ProgramRepository;
import com.codealpha.attendance.repository.UserRepository;
import com.codealpha.attendance.service.studentservice.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;

    public StudentController(StudentService studentService, UserRepository userRepository, ProgramRepository programRepository) {
        this.studentService = studentService;
        this.userRepository = userRepository;
        this.programRepository = programRepository;
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        // Validate Program ID
        Program program = programRepository.findById(student.getProgram().getProgramId()).orElse(null);
        if (program == null) {
            return new ResponseEntity<>(new ErrorResponse("Program not found", "The program ID provided does not exist."), HttpStatus.BAD_REQUEST);
        }

        // Validate User ID
        User user = userRepository.findById(student.getUser().getUserId()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new ErrorResponse("User not found", "The user ID provided does not exist."), HttpStatus.BAD_REQUEST);
        }

        // Set validated program and user
        student.setProgram(program);
        student.setUser(user);

        // Save the student
        Student savedStudent = studentService.saveStudent(student);
        if (savedStudent == null) {
            return new ResponseEntity<>(new ErrorResponse("Failed to insert student", "There was an issue while saving the student to the database."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // Handling common errors globally
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalError(Exception e) {
        return new ResponseEntity<>(new ErrorResponse("Unexpected error", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handling invalid request body (400 error)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse("Validation error", "Invalid data provided in the request body."), HttpStatus.BAD_REQUEST);
    }


    // GET endpoint to fetch all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();  // Calling the service to get all students
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no students found
        }
        return ResponseEntity.ok(students);  // Return 200 with the list of students
    }

@PutMapping("/update/{studentId}/{programId}/{userId}")
public ResponseEntity<Object> updateStudent(
        @PathVariable Long studentId,
        @PathVariable Long programId,
        @PathVariable Long userId,
        @RequestBody Student updatedStudent) {
    try {
        // Validate Student
        Student existingStudent = studentService.findByStudentProgramAndUser(studentId, programId, userId);
               if(existingStudent==null){
                System.out.println("User Does NOt EXist!!");
                return null;
               }
        // Update only allowed fields
        existingStudent.setFirstName(updatedStudent.getFirstName());
        existingStudent.setLastName(updatedStudent.getLastName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setPhoneNumber(updatedStudent.getPhoneNumber());
        existingStudent.setEnrollmentDate(updatedStudent.getEnrollmentDate());

        // Save updated student
        Student updated = studentService.saveStudent(existingStudent);

        return ResponseEntity.ok(updated);
    } catch (IllegalArgumentException e) {
        // Handle validation or not-found cases
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Update failed", e.getMessage()));
    } catch (Exception e) {
        // Handle unexpected errors
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Unexpected error", e.getMessage()));
    }
}

@DeleteMapping("/delete/{studentId}")
public ResponseEntity<Object> removeStudentById(@PathVariable Long studentId) {
    try {
        // Check if the student exists by ID
        Optional<Student> studentOptional = studentService.findStudentById(studentId);
        
        if (studentOptional.isPresent()) {
            // Student found, proceed to delete
            studentService.deleteStudentById(studentId);

            // Return success response
            return ResponseEntity.ok("Student deleted successfully");
        } else {
            // Student not found, return error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Student not found with ID: " + studentId, null));
        }
    } catch (Exception e) {
        // Handle unexpected errors
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Unexpected error", e.getMessage()));
    }
}
 
// Get the total number of students
@GetMapping("/count")
public long getTotalStudents(Student Long) {
    return studentService.count(Long);
}

 // Endpoint to get the sum of students grouped by programId
 @GetMapping("/programId/sum")
 public List<Object[]> getSumGroupedByProgramId() {
     return studentService.getSumGroupedByProgramId();
 }

 @GetMapping("/search")
 public List<Student> searchStudents(
         @RequestParam(required = false) String email,
         @RequestParam(required = false) String studentId,
         @RequestParam(required = false) Long programId,
         @RequestParam(required = false) Long userId) {
     return studentService.searchStudents(email, studentId, programId, userId);
 }
}
    

