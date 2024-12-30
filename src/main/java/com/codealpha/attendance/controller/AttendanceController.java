package com.codealpha.attendance.controller;

import com.codealpha.attendance.dto.AttendanceDTO;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;
import com.codealpha.attendance.service.attendanceService.AttendanceService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    // **1. Get Attendance by Class ID**
    @GetMapping("/class/{classId}")
    public ResponseEntity<?> getAttendanceByClassId(@PathVariable Long classId) {
        try {
            List<AttendanceDTO> attendance = attendanceService.getAttendanceByClassId(classId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching attendance: " + e.getMessage());
        }
    }

    // **2. Get Attendance by Date Range**
    @GetMapping("/date-range")
    public ResponseEntity<?> getAttendanceByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        try {
            List<AttendanceDTO> attendance = attendanceService.getAttendanceByDateRange(startDate, endDate);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching attendance: " + e.getMessage());
        }
    }

    // **3. Get Attendance by Student ID**
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getAttendanceByStudentId(@PathVariable Long studentId) {
        try {
            List<AttendanceDTO> attendance = attendanceService.getAttendanceByStudentId(studentId);
            return ResponseEntity.ok(attendance);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching attendance: " + e.getMessage());
        }
    }

    // **4. Record Attendance**
    @PostMapping("/record/{classId}")
    public ResponseEntity<?> recordAttendance(
            @PathVariable Long classId,
            @RequestBody List<AttendanceDTO> attendanceRecords,
            HttpSession session) {

        try {
            User sessionUser = (User) session.getAttribute("user");
            if (sessionUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Please log in first.");
            }
            
            // Role Validation
            if (sessionUser.getRole() != UserRole.INSTRUCTOR) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Access Denied: Only INSTRUCTORS can record attendance.");
            }
            
            // **Log Debugging Info**
            System.out.println("Instructor ID: " + sessionUser.getUserId());
            System.out.println("Instructor Username: " + sessionUser.getUsername());
            System.out.println("Class ID: " + classId);
            System.out.println("Received Attendance Records: " + attendanceRecords);

            // **Save Attendance Records**
            attendanceService.recordAttendance(classId, attendanceRecords, session);

            return ResponseEntity.ok("Attendance records saved successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log full stack trace for debugging
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error saving attendance: " + e.getMessage());
        }
    }

    // **5. Count Total Attendance Records**
    @GetMapping("/count")
    public ResponseEntity<Long> countAttendanceRecords() {
        try {
            long count = attendanceService.countAttendanceRecords();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(0L); // Default value in case of error
        }
    }

    // **6. Count PRESENT Records**
    @GetMapping("/count/present")
    public ResponseEntity<Long> countPresentAttendance() {
        try {
            long count = attendanceService.countPresentRecords();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(0L); // Default value in case of error
        }
    }

    // **7. Count ABSENT Records**
    @GetMapping("/count/absent")
    public ResponseEntity<Long> countAbsentAttendance() {
        try {
            long count = attendanceService.countAbsentRecords();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(0L); // Default value in case of error
        }
    }

    // **8. Count LATE Records**
    @GetMapping("/count/late")
    public ResponseEntity<Long> countLateAttendance() {
        try {
            long count = attendanceService.countLateRecords();
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(0L); // Default value in case of error
        }
    }
}
