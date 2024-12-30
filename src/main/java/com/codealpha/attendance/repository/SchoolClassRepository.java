package com.codealpha.attendance.repository;
import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    Optional<SchoolClass> findByClassIdAndInstructor(Long classId, User instructor);

    Optional<SchoolClass> findByInstructorUserIdAndCourseCourseId(Long userId, Long courseId);
}