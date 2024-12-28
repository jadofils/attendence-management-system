package com.codealpha.attendance.service.schoolService;

import com.codealpha.attendance.dto.SchoolClassDTO;
import com.codealpha.attendance.model.Course;
import com.codealpha.attendance.model.Program;
import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.model.User;
import com.codealpha.attendance.model.UserRole;
import com.codealpha.attendance.repository.CourseRepository;
import com.codealpha.attendance.repository.ProgramRepository;
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
    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<SchoolClass> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass createClass(Long userId, SchoolClassDTO schoolClassDTO) {

        // 1. Check if instructor exists
        User instructor = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Instructor not found with id: " + userId));

        // 2. Validate instructor role
        if (instructor.getRole() != UserRole.INSTRUCTOR) {
            throw new RuntimeException("User is not an instructor");
        }

        Program program = programRepository.findById(schoolClassDTO.getProgramId())
                .orElseThrow(() -> new RuntimeException("Program not found with id: " + schoolClassDTO.getProgramId()));

        // 4. Check if courseId exists
        Course course = courseRepository.findById(schoolClassDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + schoolClassDTO.getCourseId()));

        // 5. Map DTO to Entity
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setClassCode(schoolClassDTO.getClassCode());
        schoolClass.setClassSchedule(schoolClassDTO.getClassSchedule());
        schoolClass.setInstructor(instructor);
        schoolClass.setCourse(course);
        schoolClass.setProgram(program);

        // 6. Save and return
        try {
            return schoolClassRepository.save(schoolClass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create class", e);
        }
    }

    @Override
    public List<User> getAllInstructors() {
        return userRepository.findByRole(UserRole.INSTRUCTOR);
    }

    @Override
    public void deleteScheduled(Long userId, Long classId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != UserRole.INSTRUCTOR && user.getRole() != UserRole.ADMINISTRATOR) {
            throw new RuntimeException("Only instructors or administrators can delete scheduled classes");
        }

        SchoolClass scheduledClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (!scheduledClass.getInstructor().getUserId().equals(userId)) {
            throw new RuntimeException("This class is not assigned to you");
        }

        schoolClassRepository.delete(scheduledClass);
    }

    @Override
    public SchoolClass updateClass(Long userId, Long classId, SchoolClassDTO schoolClassDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != UserRole.INSTRUCTOR && user.getRole() != UserRole.ADMINISTRATOR) {
            throw new RuntimeException("Only instructors or administrators can update scheduled classes");
        }

        SchoolClass scheduledClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (user.getRole() == UserRole.INSTRUCTOR &&
                !scheduledClass.getInstructor().getUserId().equals(userId)) {
            throw new RuntimeException("This class is not assigned to you");
        }

        scheduledClass.setClassCode(schoolClassDTO.getClassCode());
        scheduledClass.setClassSchedule(schoolClassDTO.getClassSchedule());

        if (schoolClassDTO.getCourseId() != null) {
            Course course = courseRepository.findById(schoolClassDTO.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));
            scheduledClass.setCourse(course);
        }

        return schoolClassRepository.save(scheduledClass);
    }

    @Override
    public long count() {
        return schoolClassRepository.count();
    }

    @Override
    public SchoolClass getClassById(Long classId) {
        return schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with id " + classId));
    }

}
