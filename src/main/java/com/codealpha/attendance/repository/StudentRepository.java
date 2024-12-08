package com.codealpha.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codealpha.attendance.model.Student;
import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT DISTINCT s FROM Student s " +
           "LEFT JOIN FETCH s.program " +
           "LEFT JOIN FETCH s.courses " +
           "LEFT JOIN FETCH s.attendanceRecords")
    List<Student> findAllStudentsWithProgramAndCoursesAndAttendance();
}