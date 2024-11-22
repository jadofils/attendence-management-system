package com.codealpha.attendance.service.courseService;

import com.codealpha.attendance.model.Course;

import java.util.List;

public interface CourseService {
    Course createCourse(Course course, Long programId);
    Course updateCourse(Long courseId, Course updatedCourse);
    void deleteCourse(Long courseId);
    Course getCourseById(Long courseId);
    Course getCourseByName(String courseName);
    Long countCourses();
    List<Course> getAllCourses();
}
