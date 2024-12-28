package com.codealpha.attendance.controller;

import com.codealpha.attendance.dto.SchoolClassDTO;
import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.service.courseService.CourseService;
import com.codealpha.attendance.service.programservice.ProgramService;
import com.codealpha.attendance.service.schoolService.SchoolClassService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class SchoolClassController {
    @Autowired
    private ProgramService programService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SchoolClassService schoolClassService;
    // Endpoint to get all classes
    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> getAllClasses() {
        List<SchoolClassDTO> classDTOs = schoolClassService.getAllClasses()
                .stream()
                .map(schoolClass -> {
                    SchoolClassDTO dto = new SchoolClassDTO();
                    dto.setClassId(schoolClass.getClassId());
                    dto.setClassCode(schoolClass.getClassCode());
                    dto.setClassSchedule(schoolClass.getClassSchedule());
    
                    // Set Course details
                    dto.setCourseId(schoolClass.getCourse().getCourseId());
                    dto.setCourseName(schoolClass.getCourse().getCourseName()); // Add name
    
                    // Set Program details
                    dto.setProgramId(schoolClass.getProgram().getProgramId());
                    dto.setProgramName(schoolClass.getProgram().getProgramName()); // Add name
    
                    // Set Instructor details
                    dto.setInstructorId(schoolClass.getInstructor().getUserId());
                    dto.setInstructorName(schoolClass.getInstructor().getUsername()); // Add name
    
                    return dto;
                })
                .toList();
    
        return ResponseEntity.ok(classDTOs);
    }
    

 
    
        
    
        @PostMapping("/{instructorId}")
        public ResponseEntity<?> createClass(
                @PathVariable Long instructorId,
                @RequestBody @Valid SchoolClassDTO schoolClassDTO) {

System.out.println("Received DTO: " + schoolClassDTO);
System.out.println("Program ID: " + schoolClassDTO.getProgramId());
System.out.println("Course ID: " + schoolClassDTO.getCourseId());
System.out.println("Instructor ID: " + schoolClassDTO.getInstructorId());
System.out.println("Class Code: " + schoolClassDTO.getClassCode());
System.out.println("Class Schedule: " + schoolClassDTO.getClassSchedule());

    
            try {
                // Validation: Check if programId and courseId are provided
                if (schoolClassDTO.getProgramId() == null || schoolClassDTO.getCourseId() == null) {
                    return ResponseEntity.badRequest().body("Program ID and Course ID cannot be null!");
                }
    
                // Check if the Program exists
                if (!programService.existsById(schoolClassDTO.getProgramId())) {
                    return ResponseEntity.badRequest().body("Program ID does not exist!");
                }
    
                // Check if the Course exists
                if (!courseService.existsById(schoolClassDTO.getCourseId())) {
                    return ResponseEntity.badRequest().body("Course ID does not exist!");
                }
    
                // Create the class
                SchoolClass createdClass = schoolClassService.createClass(instructorId, schoolClassDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdClass);
    
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    
    

    // Endpoint to delete a scheduled class
    @DeleteMapping("/{instructorId}/{classId}")
    public ResponseEntity<String> deleteScheduledClass(@PathVariable Long instructorId, @PathVariable Long classId) {
        try {
            schoolClassService.deleteScheduled(instructorId, classId);
            return ResponseEntity.ok("Scheduled class successfully deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to update a class
    @PutMapping("/{instructorId}/{classId}")
    public ResponseEntity<?> updateClass(@PathVariable Long instructorId,
                                         @PathVariable Long classId,
                                         @RequestBody @Valid SchoolClassDTO schoolClassDTO) {
        try {
            SchoolClass updatedClass = schoolClassService.updateClass(instructorId, classId, schoolClassDTO);
            return ResponseEntity.ok(updatedClass);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Endpoint to count all scheduled classes
    @GetMapping("/count-classes")
    public long count() {
        return schoolClassService.count();
    }

    // Endpoint to get a class by ID
    @GetMapping("/{classId}")
    public ResponseEntity<?> getClassById(@PathVariable Long classId) {
        try {
            SchoolClass schoolClass = schoolClassService.getClassById(classId);
            return ResponseEntity.ok(schoolClass);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
