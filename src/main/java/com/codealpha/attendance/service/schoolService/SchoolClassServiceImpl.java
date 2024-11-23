package com.codealpha.attendance.service.schoolService;

import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;
import com.codealpha.attendance.repository.CourseRepository;
import com.codealpha.attendance.repository.SchoolClassRepository;
import com.codealpha.attendance.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolClassServiceImpl implements SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass createClass(Long userId, Long courseId, SchoolClass schoolClass) {
        User instructor = userRepository.findById(userId).orElseThrow(() -> 
                new RuntimeException("Instructor not found with id: " + userId));
        
        if (instructor.getRole() != UserRole.INSTRUCTOR) {
            throw new RuntimeException("User is not an instructor");
        }
    
        Course course = courseRepository.findById(courseId).orElseThrow(() -> 
                new RuntimeException("Course not found with id: " + courseId));
    
        schoolClass.setInstructor(instructor);
        schoolClass.setCourse(course);
    
        try {
            return schoolClassRepository.save(schoolClass);
        } catch (Exception e) {
            return null; // Return null if insertion fails
        }
    }
    
    @Override
public void deleteScheduled(Long userId, Long classId) {
    // Retrieve the user
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.getRole() != UserRole.INSTRUCTOR && user.getRole() != UserRole.ADMINISTRATOR) {
                throw new RuntimeException("Only instructors or administrators can delete scheduled classes");
            }
            
            

    // Retrieve the class
    SchoolClass scheduledClass = schoolClassRepository.findById(classId)
            .orElseThrow(() -> new RuntimeException("Class not found"));

    // Check if the class is assigned to this instructor
    if (!scheduledClass.getInstructor().getUserId().equals(userId)) {
        throw new RuntimeException("This class is not assigned to you");
    }

    // Delete the scheduled class
    schoolClassRepository.delete(scheduledClass);
}

}
