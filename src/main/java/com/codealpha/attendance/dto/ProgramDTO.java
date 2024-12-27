package com.codealpha.attendance.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProgramDTO {
    private Long programId;                          // Program ID
    private String programName;                      // Program Name
    private String programDescription;               // Description

    // Update to include both ID and Name
    private List<CourseDTO> courses;                 // List of Courses with ID and Name

    private List<String> studentNames;               // Optional students
}
