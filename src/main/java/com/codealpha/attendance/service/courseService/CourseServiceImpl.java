package com.codealpha.attendance.service.courseService;


import com.codealpha.attendance.dto.CourseDTO;
import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.repository.CourseRepository;
import com.codealpha.attendance.repository.ProgramRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Program program = programRepository.findById(courseDTO.getProgramId()).orElseThrow(() ->
            new IllegalArgumentException("Program with ID " + courseDTO.getProgramId() + " does not exist."));
        
        Course course = Course.builder()
            .courseName(courseDTO.getCourseName())
            .courseDescription(courseDTO.getCourseDescription())
            .credits(courseDTO.getCredits())
            .program(program)
            .build();
        
        return convertToDTO(courseRepository.save(course));
    }

    @Override
    public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
        Course existingCourse = courseRepository.findById(courseId).orElseThrow(() -> 
            new IllegalArgumentException("Course with ID " + courseId + " does not exist."));
        
        Program program = programRepository.findById(courseDTO.getProgramId()).orElseThrow(() ->
            new IllegalArgumentException("Program with ID " + courseDTO.getProgramId() + " does not exist."));
        
        existingCourse.setCourseName(courseDTO.getCourseName());
        existingCourse.setCourseDescription(courseDTO.getCourseDescription());
        existingCourse.setCredits(courseDTO.getCredits());
        existingCourse.setProgram(program);
        
        return convertToDTO(courseRepository.save(existingCourse));
    }

    
    @Override
    public CourseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> 
            new IllegalArgumentException("Course with ID " + courseId + " does not exist."));
        return convertToDTO(course);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCourse(Long courseId) {
        Course existingCourse = courseRepository.findById(courseId).orElseThrow(() ->
            new IllegalArgumentException("Course with ID " + courseId + " does not exist."));
        
        courseRepository.delete(existingCourse);
    }

    private CourseDTO convertToDTO(Course course) {
        return CourseDTO.builder()
            .courseId(course.getCourseId())
            .courseName(course.getCourseName())
            .courseDescription(course.getCourseDescription())
            .credits(course.getCredits())
            .programId(course.getProgram().getProgramId())
            .build();
    }
}
