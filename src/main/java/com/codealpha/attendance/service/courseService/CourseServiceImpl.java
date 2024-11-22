package com.codealpha.attendance.service.courseService;

import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.repository.CourseRepository;
import com.codealpha.attendance.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Override
    public Course createCourse(Course course, Long programId) {
        if (courseRepository.existsByCourseName(course.getCourseName())) {
            throw new IllegalArgumentException("A course with this name already exists.");
        }

        Program program = programRepository.findById(programId)
            .orElseThrow(() -> new IllegalArgumentException("Program not found with ID: " + programId));

        course.setProgram(program);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long courseId, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));

        if (!existingCourse.getCourseName().equals(updatedCourse.getCourseName()) &&
            courseRepository.existsByCourseName(updatedCourse.getCourseName())) {
            throw new IllegalArgumentException("A course with this name already exists.");
        }

        existingCourse.setCourseName(updatedCourse.getCourseName());
        existingCourse.setCourseDescription(updatedCourse.getCourseDescription());
        existingCourse.setCredits(updatedCourse.getCredits());
        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        courseRepository.delete(course);
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
            .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
    }

    @Override
    public Course getCourseByName(String courseName) {
        return courseRepository.findByCourseName(courseName)
            .orElseThrow(() -> new IllegalArgumentException("Course not found with name: " + courseName));
    }

    @Override
    public Long countCourses() {
        return courseRepository.count();
    }

    

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
