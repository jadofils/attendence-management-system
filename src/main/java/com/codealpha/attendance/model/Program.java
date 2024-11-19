package com.codealpha.attendance.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Program.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    
    @Column(nullable = false, unique = true, length = 100)
    private String programName;
    
    @Column(columnDefinition = "TEXT")
    private String programDescription;
    
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Course> courses;
    
    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Student> students;
}
