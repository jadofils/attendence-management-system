package com.codealpha.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.model.Student;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s " +
           "JOIN FETCH s.program p " +
           "JOIN FETCH s.courses c " +
           "LEFT JOIN FETCH s.attendanceRecords a")
    List<Student> findAllStudentsWithProgramAndCoursesAndAttendance();
}
