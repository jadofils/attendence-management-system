package com.codealpha.attendance.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

// Course.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    
    @Column(nullable = false, length = 100)
    private String courseName;
    
    @Column(columnDefinition = "TEXT")
    private String courseDescription;
    
    @Column(nullable = false)
    private Integer credits;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<SchoolClass> classes;
}