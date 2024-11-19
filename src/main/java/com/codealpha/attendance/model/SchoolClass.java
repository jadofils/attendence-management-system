package com.codealpha.attendance.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
// Class.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classId;
    
    @Column(nullable = false, unique = true, length = 20)
    private String classCode;
    
    @Column(nullable = false)
    private String classSchedule;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;
    
    @OneToMany(mappedBy = "attendedClass", cascade = CascadeType.ALL)
    private List<Attendance> attendanceRecords;
}
