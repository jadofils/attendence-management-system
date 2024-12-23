package com.codealpha.attendance.controller.ViewControllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.codealpha.attendance.dto.CourseDTO;
import com.codealpha.attendance.dto.ProgramDTO;
import com.codealpha.attendance.dto.StudentDTO;
import com.codealpha.attendance.dto.UserDTO;
import com.codealpha.attendance.service.courseService.CourseService;
import com.codealpha.attendance.service.programservice.ProgramService;
import com.codealpha.attendance.service.studentservice.StudentService;
import com.codealpha.attendance.service.userservice.UserService;


@Controller
@RequestMapping("/students")
public class StudentViewController {

    @Autowired
    private RestTemplate restTemplate;

private StudentService studentService;
private UserService userService;
private ProgramService programService;
private CourseService courseService;

    @SuppressWarnings("null")
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
            if (student.getProgramId() != null) {
                ResponseEntity<ProgramDTO> programResponse = restTemplate.getForEntity(
                    programApiUrl + student.getProgramId(), ProgramDTO.class);
                student.setProgramName(programResponse.getBody().getProgramName());
            }
    
            if (student.getUserId() != null) {
                ResponseEntity<UserDTO> userResponse = restTemplate.getForEntity(
                    userApiUrl + student.getUserId(), UserDTO.class);
                UserDTO user = userResponse.getBody();
                student.setUserName(user.getUsername());
            }
    
            List<String> courseNames = new ArrayList<>();
            if (student.getCourseIds() != null) {
                for (Long courseId : student.getCourseIds()) {
                    String courseName = restTemplate.getForObject(courseApiUrl + courseId, String.class);
                    courseNames.add(courseName);
                }
            }
            student.setCourseNames(courseNames);
        }
    
        model.addAttribute("students", students);
        model.addAttribute("studentDTO", new StudentDTO());

        // Fetch dropdown data
        addDropdownData(model, students);
    
        return "students/select";
    }

    @GetMapping("/form")
    public String showStudentForm(Model model) {
        model.addAttribute("studentDTO", new StudentDTO());
        addDropdownData(model, new ArrayList<>());
        return "students/select";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute StudentDTO studentDTO, @RequestParam Long userId, Model model) {
        try {
            String apiUrl = "http://localhost:8080/api/students/" + userId;
            restTemplate.postForEntity(apiUrl, studentDTO, StudentDTO.class);
            model.addAttribute("success", "Student added successfully!");
            getAllStudents(model);
            return "students/select";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding student: " + e.getMessage());
            getAllStudents(model);
            return "students/select";
        }
    }

    @GetMapping("/view/{studentId}")
    @ResponseBody
    public StudentDTO viewStudentDetails(@PathVariable Long studentId) {
        String studentApiUrl = "http://localhost:8080/api/students/" + studentId;
        ResponseEntity<StudentDTO> response = restTemplate.getForEntity(studentApiUrl, StudentDTO.class);
        return response.getBody();
    }

   private void addDropdownData(Model model, List<StudentDTO> students) {
    // Fetch all users
    String userApiUrl = "http://localhost:8080/api/users";
    ResponseEntity<List<UserDTO>> userResponse = restTemplate.exchange(
        userApiUrl,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<UserDTO>>() {}
    );
    List<UserDTO> allUsers = userResponse.getBody();

    // Filter out users who already have a student
    List<Long> studentUserIds = students.stream()
                                        .map(StudentDTO::getUserId)
                                        .collect(Collectors.toList());
    @SuppressWarnings("null")
    List<UserDTO> availableUsers = allUsers.stream()
                                           .filter(user -> !studentUserIds.contains(user.getUserId()))
                                           .collect(Collectors.toList());

    model.addAttribute("users", availableUsers);

    // Fetch programs
    String programApiUrl = "http://localhost:8080/api/programs";
    ResponseEntity<List<ProgramDTO>> programResponse = restTemplate.exchange(
        programApiUrl,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<ProgramDTO>>() {}
    );
    model.addAttribute("programs", programResponse.getBody());

    // Fetch courses as List<CourseDTO>
    String courseApiUrl = "http://localhost:8080/api/courses";
    ResponseEntity<List<CourseDTO>> courseResponse = restTemplate.exchange(
        courseApiUrl,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<CourseDTO>>() {}
    );
    model.addAttribute("courses", courseResponse.getBody());
}

@GetMapping("/students/edit/{id}")
public String editStudent(@PathVariable Long id, Model model) {
    StudentDTO student = studentService.getStudentDataById(id); // Fetch student data
    model.addAttribute("studentDTO", student);

    // Static dropdown data
    model.addAttribute("users", userService.getAllUsers());
    model.addAttribute("programs", programService.getAllPrograms());
    model.addAttribute("courses", courseService.getAllCourses());

    return "students/update";
}

}

