package com.codealpha.attendance.service.attendanceService;

import com.codealpha.attendance.dto.AttendanceDTO;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    // Get attendance by class ID
    List<AttendanceDTO> getAttendanceByClassId(Long classId);

    // Get attendance by date range
    List<AttendanceDTO> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate);

    // Get attendance by student ID
    List<AttendanceDTO> getAttendanceByStudentId(Long studentId);

    // Record attendance for a class
    void recordAttendance(Long classId, List<AttendanceDTO> attendanceRecords, HttpSession session);

    // Count total attendance records
 // Count Attendance Records
 long countAttendanceRecords(); // Total
 long countPresentRecords();    // PRESENT
 long countAbsentRecords();     // ABSENT
 long countLateRecords();       // LATE
 
}
