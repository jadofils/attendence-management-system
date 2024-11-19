package com.codealpha.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codealpha.attendance.model.Course;

// CourseRepository.java
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByProgramProgramId(Long programId);
}