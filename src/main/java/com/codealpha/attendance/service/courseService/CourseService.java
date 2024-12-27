package com.codealpha.attendance.service.courseService;


import com.codealpha.attendance.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    CourseDTO createCourse(CourseDTO courseDTO,Long programId);

    CourseDTO updateCourse(Long courseId, CourseDTO courseDTO);

    CourseDTO getCourseById(Long courseId);

    List<CourseDTO> getAllCourses();

    void deleteCourse(Long courseId);

    boolean existsByCourseName(String courseName);

    boolean existsById(Long courseId);
}
