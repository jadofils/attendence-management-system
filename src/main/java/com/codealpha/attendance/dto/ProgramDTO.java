package com.codealpha.attendance.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProgramDTO {
    private Long programId;                   // Expose the program ID
    private String programName;               // Expose the program name
    private String programDescription;        // Expose the description
    private List<String> courseNames;         // Expose course names (if needed)
    private List<String> studentNames;        // Expose student names (if needed)
}
