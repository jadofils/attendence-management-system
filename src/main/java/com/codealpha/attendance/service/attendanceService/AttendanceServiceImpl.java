package com.codealpha.attendance.service.attendanceService;

import com.codealpha.attendance.dto.AttendanceDTO;
import com.codealpha.attendance.model.*;
import com.codealpha.attendance.repository.AttendanceRepository;
import com.codealpha.attendance.repository.SchoolClassRepository;
import com.codealpha.attendance.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    // **1. Get Attendance by Class ID**
    @Override
    public List<AttendanceDTO> getAttendanceByClassId(Long classId) {
        // Fetch attendance records by class ID
        return attendanceRepository.findByAttendedClassClassId(classId).stream()
                .map(this::mapToDTO) // Map to DTO
                .collect(Collectors.toList());
    }

    // **2. Get Attendance by Date Range**
    @Override
    public List<AttendanceDTO> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
        // Fetch attendance between dates
        return attendanceRepository.findByAttendanceDateBetween(startDate, endDate).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // **3. Get Attendance by Student ID**
    @Override
    public List<AttendanceDTO> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentStudentId(studentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void recordAttendance(Long classId, List<AttendanceDTO> attendanceRecords, HttpSession session) {
        // **Session Validation** - Check if user is logged in
        User sessionUser = (User) session.getAttribute("user");
    java.lang.System.out.println("the session data is:"+sessionUser);
        if (sessionUser == null) {
            throw new RuntimeException("Unauthorized: Please log in first.");
        }
    
        // **Role Validation** - Only INSTRUCTOR can record attendance
        if (!UserRole.INSTRUCTOR.equals(sessionUser.getRole())) {
            throw new RuntimeException("Access Denied: Only INSTRUCTORS can record attendance.");
        }
    
        // Fetch Class
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + classId));
    
        List<Attendance> attendanceEntities = new ArrayList<>();
    
        for (AttendanceDTO dto : attendanceRecords) {
            // Validate Student
            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found with ID: " + dto.getStudentId()));
    
            // Convert Status
            AttendanceStatus status;
            try {
                status = AttendanceStatus.valueOf(dto.getAttendanceStatus().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid attendance status: " + dto.getAttendanceStatus());
            }
    
            // Prevent Duplicate Entries
            boolean exists = attendanceRepository.existsByAttendanceDateAndStudentAndAttendedClass(
                    dto.getAttendanceDate(), student, schoolClass);
    
            if (exists) {
                throw new RuntimeException("Attendance already recorded for student ID: " + dto.getStudentId());
            }
    
            // Create Attendance Entity
            Attendance attendance = Attendance.builder()
                    .attendanceDate(dto.getAttendanceDate())
                    .attendanceStatus(status)
                    .student(student)
                    .attendedClass(schoolClass)
                    .build();
    
            attendanceEntities.add(attendance);
        }
    
        // Save Attendance
        attendanceRepository.saveAll(attendanceEntities);
        // System.out.println("Attendance saved successfully!");
    }
    

    // **5. Count Total Attendance Records**
    @Override
    public long countAttendanceRecords() {
        return attendanceRepository.count();
    }

     // **Count PRESENT Records**
     @Override
     public long countPresentRecords() {
         return attendanceRepository.findAll().stream()
                 .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.PRESENT)
                 .count();
     }
 
     // **Count ABSENT Records**
     @Override
     public long countAbsentRecords() {
         return attendanceRepository.findAll().stream()
                 .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.ABSENT)
                 .count();
     }
 
     // **Count LATE Records**
     @Override
     public long countLateRecords() {
         return attendanceRepository.findAll().stream()
                 .filter(attendance -> attendance.getAttendanceStatus() == AttendanceStatus.LATE)
                 .count();
     }

    private AttendanceDTO mapToDTO(Attendance attendance) {
        return AttendanceDTO.builder()
                .attendanceId(attendance.getAttendanceId())
                .attendanceDate(attendance.getAttendanceDate())
                .classId(attendance.getAttendedClass().getClassId())
                .className(attendance.getAttendedClass().getClassCode()) // Maps class name
                .studentId(attendance.getStudent().getStudentId())       // Maps student ID
                .studentName(attendance.getStudent().getLastName() + " " + attendance.getStudent().getFirstName()) 
                .attendanceStatus(attendance.getAttendanceStatus().name()) // Enum to String
                .presentCount(attendance.getAttendanceStatus() == AttendanceStatus.PRESENT ? 1L : 0L)
                .absentCount(attendance.getAttendanceStatus() == AttendanceStatus.ABSENT ? 1L : 0L)
                .totalStudents(1L) // Assuming each DTO represents one student
                .build();
    }

    
}
