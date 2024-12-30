package com.codealpha.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for SchoolClass
 */
@Data                     // Generates Getters, Setters, equals(), hashCode(), and toString()
@NoArgsConstructor        // Generates a no-argument constructor
@AllArgsConstructor       // Generates a constructor with all arguments
@Builder                  // Generates the Builder pattern
public class SchoolClassDTO {

    private Long classId;
    private String classCode;
    private String classSchedule;

    // Course Details
    private Long courseId;
    private String courseName;

    // Program Details
    private Long programId;
    private String programName;

    // Instructor Details
    private Long instructorId;
    private String instructorName;
}
