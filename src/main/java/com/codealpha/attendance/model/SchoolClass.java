package com.codealpha.attendance.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false) // Added Program relationship
    private Program program;

    @OneToMany(mappedBy = "attendedClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendanceRecords;
}
