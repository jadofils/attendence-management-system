package com.codealpha.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private Integer credits;
    private Long programId; // Refers to the associated Program
}
