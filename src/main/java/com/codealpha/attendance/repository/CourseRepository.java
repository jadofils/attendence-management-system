package com.codealpha.attendance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codealpha.attendance.model.Course;

// CourseRepository.java
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByProgramProgramId(Long programId);

    boolean existsByCourseName(String courseName);

    Optional<Course> findByCourseName(String courseName);
}