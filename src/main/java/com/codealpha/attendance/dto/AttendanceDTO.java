package com.codealpha.attendance.dto;

import java.time.LocalDate;

import com.codealpha.attendance.model.AttendanceStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDTO {
    private Long attendanceId;
    private LocalDate attendanceDate;
    private AttendanceStatus attendanceStatus;
    private Long studentId;      // Reference to Student ID
    private Long classId;        // Reference to Class ID
    private String studentName;  // Optional: Include student name for display purposes
    private String className;    // Optional: Include class name for display purposes
}
