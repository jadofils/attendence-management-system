package com.codealpha.attendance.controller;

import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.model.Attendance;
import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.model.Student;
import com.codealpha.attendance.service.attendanceService.AttendanceService;
import com.codealpha.attendance.service.schoolService.SchoolClassService;
import com.codealpha.attendance.service.studentservice.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SchoolClassService schoolClassService;

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/{classid}/{studentid}")
    public ResponseEntity<Attendance> addAttendance(
            @PathVariable Long classid, 
            @PathVariable Long studentid, 
            @RequestBody Attendance attendanceRequest) {
        
        try {
            // Fetch the student and class from the database using the provided IDs
            Student student = studentService.getStudentById(studentid);
            SchoolClass attendedClass = schoolClassService.getClassById(classid);

            // Ensure the entities exist
            if (student == null || attendedClass == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Or return a specific error message
            }

            // Create a new Attendance object
            Attendance attendance = Attendance.builder()
                    .attendanceDate(attendanceRequest.getAttendanceDate())
                    .attendanceStatus(attendanceRequest.getAttendanceStatus())
                    .student(student)  // Set the existing student
                    .attendedClass(attendedClass)  // Set the existing class
                    .build();

            // Save the attendance record
            Attendance savedAttendance = attendanceService.saveAttendance(attendance);

            // Return the saved attendance object with HTTP status 201
            return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception and return a generic error response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{attendanceId}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long attendanceId) {
        Optional<Attendance> attendance = attendanceService.getAttendanceById(attendanceId);
        
        if (attendance.isPresent()) {
            return ResponseEntity.ok(attendance.get()); // Return the attendance if found
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }
    
    @GetMapping("/all")
    public List<Attendance> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }
}
