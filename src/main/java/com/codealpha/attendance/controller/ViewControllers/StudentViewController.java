package com.codealpha.attendance.controller.ViewControllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Correct import
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.codealpha.attendance.dto.ProgramDTO;
import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.dto.UserDTO;


@Controller
@RequestMapping("/students")
public class StudentViewController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String getAllStudents(Model model) {
        String studentApiUrl = "http://localhost:8080/api/students/all";
        String programApiUrl = "http://localhost:8080/api/programs/";
        String userApiUrl = "http://localhost:8080/api/users/";
        String courseApiUrl = "http://localhost:8080/api/courses/";
    
        // Fetch students
        ResponseEntity<List<StudentDTO>> studentResponse = restTemplate.exchange(
            studentApiUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<StudentDTO>>() {}
        );
    
        List<StudentDTO> students = studentResponse.getBody();
    
        // Enrich each student with additional details
        for (StudentDTO student : students) {
            // Fetch and set program name
            if (student.getProgramId() != null) {
                ResponseEntity<ProgramDTO> programResponse = restTemplate.getForEntity(
                    programApiUrl + student.getProgramId(), ProgramDTO.class);
                student.setProgramName(programResponse.getBody().getProgramName());
            }
    
            // Fetch and set user details
            if (student.getUserId() != null) {
                ResponseEntity<UserDTO> userResponse = restTemplate.getForEntity(
                    userApiUrl + student.getUserId(), UserDTO.class);
                UserDTO user = userResponse.getBody();
                student.setUserName(user.getUsername());
            }
    
            // Fetch and set course names
            List<String> courseNames = new ArrayList<>();
            if (student.getCourseIds() != null) {
                for (Long courseId : student.getCourseIds()) {
                    String courseName = restTemplate.getForObject(courseApiUrl + courseId, String.class);
                    courseNames.add(courseName);
                }
            }
            student.setCourseNames(courseNames);
        }
    
        // Add the enriched students to the model
        model.addAttribute("students", students);
    
        return "students/select";
    }
}    