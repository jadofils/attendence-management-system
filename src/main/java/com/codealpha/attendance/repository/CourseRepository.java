package com.codealpha.attendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codealpha.attendance.model.Course;

// CourseRepository.java
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByCourseName(String courseName);

    boolean existsByCourseNameAndProgramProgramId(String courseName, Long programId);
  
}