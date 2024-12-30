package com.codealpha.attendance.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Long attendanceId;                     // Added ID
    private LocalDate attendanceDate;               // Date of attendance
    private Long classId;                           // Class ID
    private String className;                       // Class name
    private Long totalStudents;                     // Total students
    private Long presentCount;                      // Present count
    private Long absentCount;                       // Absent count

    // Added fields
    private Long studentId;                         // Student ID
    private String studentName;                     // Student Name
    private String attendanceStatus;                // Attendance Status (PRESENT, ABSENT, LATE)
    private List<AttendanceDTO> attendanceRecords;  // Nested attendance records
}
