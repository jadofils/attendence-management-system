package com.codealpha.attendance.model;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
    name = "courses",
    uniqueConstraints = @UniqueConstraint(
        name = "unique_course_name_per_program", 
        columnNames = {"course_name", "program_id"}
    )
)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(nullable = false, length = 100)
    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String courseDescription;

    @Column(nullable = false)
    private Integer credits;

    // Foreign key with cascade delete
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "program_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_program_course")
    )
    private Program program;

    // One-to-Many relationship with classes
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchoolClass> classes;

    // Many-to-Many relationship with students (no cascade)
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
