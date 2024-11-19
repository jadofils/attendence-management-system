package com.codealpha.attendance.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codealpha.attendance.model.SchoolClass;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    List<SchoolClass> findByCourseCourseId(Long courseId);
    List<SchoolClass> findByInstructorUserId(Long instructorId);
}
