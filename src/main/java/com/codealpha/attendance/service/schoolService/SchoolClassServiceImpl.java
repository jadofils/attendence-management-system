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



@Override
public SchoolClass updateClass(Long userId, Long classId, SchoolClass updatedSchoolClass) {
    // Retrieve the user
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Check if the user has the appropriate role
    if (user.getRole() != UserRole.INSTRUCTOR && user.getRole() != UserRole.ADMINISTRATOR) {
        throw new RuntimeException("Only instructors or administrators can update scheduled classes");
    }

    // Retrieve the class
    SchoolClass scheduledClass = schoolClassRepository.findById(classId)
            .orElseThrow(() -> new RuntimeException("Class not found"));

    // Check if the class is assigned to this instructor (only if the user is an instructor)
    if (user.getRole() == UserRole.INSTRUCTOR && 
        !scheduledClass.getInstructor().getUserId().equals(userId)) {
        throw new RuntimeException("This class is not assigned to you");
    }

    // Update only non-referenced fields
    scheduledClass.setClassCode(updatedSchoolClass.getClassCode());
    scheduledClass.setClassSchedule(updatedSchoolClass.getClassSchedule());

    // Save the updated class
    return schoolClassRepository.save(scheduledClass);
}

//count all classes scheduled
public long count(){
    return schoolClassRepository.count();
}
public SchoolClass getClassById(Long classid) {
    // Use the repository to find a class by ID
    return schoolClassRepository.findById(classid)
            .orElseThrow(() -> new RuntimeException("Class not found with id " + classid));
}

}
