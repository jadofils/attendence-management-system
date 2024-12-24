package com.codealpha.attendance.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(nullable = false, unique = true, length = 100)
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String courseDescription;

    @Column(nullable = false)
    private Integer credits;

    // Cascade delete from Program
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "program_id", 
        nullable = false, 
        foreignKey = @ForeignKey(name = "FK_program_course", value = ConstraintMode.CONSTRAINT)
    )
    private Program program;

    // Cascade delete for related classes
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchoolClass> classes;

    // Cascade delete relationships in student_courses table
    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private List<Student> students;
}
