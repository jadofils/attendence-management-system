package com.codealpha.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
  
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate enrollmentDate;
    private Long programId; // References Program ID
    private Long userId;    // References User ID
    private List<Long> courseIds; // List of Course IDs
}
