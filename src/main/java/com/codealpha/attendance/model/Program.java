package com.codealpha.attendance.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")  // Optional: explicitly name the column if needed
    private Long programId;  // This is the field name for the primary key
    
    @Column(nullable = false, unique = true, length = 100)
    private String programName;
    
    @Column(columnDefinition = "TEXT")
    private String programDescription;
    
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Course> courses;
    
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Student> students;
}
