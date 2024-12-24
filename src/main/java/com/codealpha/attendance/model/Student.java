package com.codealpha.attendance.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    // Cascade delete when program is deleted
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false, foreignKey = @ForeignKey(name = "FK_program_student", value = ConstraintMode.CONSTRAINT))
    private Program program;

    // Cascade delete when user is deleted
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_user_student", value = ConstraintMode.CONSTRAINT))
    private User user;

    // Many-to-Many relationship for courses
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "student_courses",
        joinColumns = @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "FK_student_course")),
        inverseJoinColumns = @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "FK_course_student"))
    )
    private List<Course> courses = new ArrayList<>();

    // Attendance records with cascading delete
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendanceRecords = new ArrayList<>();
}
