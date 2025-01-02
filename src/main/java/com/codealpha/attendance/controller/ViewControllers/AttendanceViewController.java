package com.codealpha.attendance.controller.ViewControllers;

import com.codealpha.attendance.dto.AttendanceDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AttendanceViewController {

    private final RestTemplate restTemplate;

    // Constructor injection for RestTemplate
    public AttendanceViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   @GetMapping("/attendance/class")
public String getAttendanceByClass(
        @RequestParam(defaultValue = "8") Long classId, Model model) {

    // API URL with dynamic parameters
    String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/attendance/class/{classId}")
            .buildAndExpand(classId)
            .toUriString();

    try {
        // Fetch attendance records
        AttendanceDTO[] response = restTemplate.getForObject(url, AttendanceDTO[].class);
        List<AttendanceDTO> attendanceList = Arrays.asList(response);

        // **Group attendance by className** (Key: className, Value: List of records)
        Map<String, List<AttendanceDTO>> groupedAttendance = attendanceList.stream()
                .collect(Collectors.groupingBy(AttendanceDTO::getClassName));

        // Add grouped data to the model
        model.addAttribute("groupedAttendance", groupedAttendance);

        // Fetch statistics
        Long totalCount = restTemplate.getForObject("http://localhost:8080/api/attendance/count", Long.class);
        Long presentCount = restTemplate.getForObject("http://localhost:8080/api/attendance/count/present", Long.class);
        Long absentCount = restTemplate.getForObject("http://localhost:8080/api/attendance/count/absent", Long.class);
        Long lateCount = restTemplate.getForObject("http://localhost:8080/api/attendance/count/late", Long.class);

        // Add counts to model
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("presentCount", presentCount);
        model.addAttribute("absentCount", absentCount);
        model.addAttribute("lateCount", lateCount);

    } catch (Exception e) {
        model.addAttribute("errorMessage", "Failed to load attendance records: " + e.getMessage());
    }

    return "attendance/select"; // Return Thymeleaf template
}

    
        // **2. Get Attendance by Student ID**
    @GetMapping("/attendance/student")
    public String getAttendanceByStudent(
            @RequestParam Long studentId, Model model) {

        // API URL
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/attendance/student/{studentId}")
                .buildAndExpand(studentId)
                .toUriString();

        try {
            // Fetch data from API
            AttendanceDTO[] response = restTemplate.getForObject(url, AttendanceDTO[].class);
            List<AttendanceDTO> attendanceList = Arrays.asList(response);

            // Pass data to the model
            model.addAttribute("attendanceList", attendanceList);
            model.addAttribute("studentId", studentId); // Pass student ID for UI display
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to load attendance records: " + e.getMessage());
        }

        return "attendance/select"; // Thymeleaf template name
    }

    // **3. Count Attendance Records**
    @GetMapping("/attendance/count")
    public String countAttendanceRecords(Model model) {
        try {
            // API URL
            String url = "http://localhost:8080/api/attendance/count";

            // Fetch data
            Long count = restTemplate.getForObject(url, Long.class);

            // Pass count to the model
            model.addAttribute("attendanceCount", count);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to count attendance records: " + e.getMessage());
        }

        return "attendance/select"; // Thymeleaf template name
    }

    // **4. Get Attendance by Date Range**
    @GetMapping("/attendance/date-range")
    public String getAttendanceByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            Model model) {

        // API URL with query parameters
        String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/attendance/date-range")
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .toUriString();

        try {
            // Fetch data from API
            AttendanceDTO[] response = restTemplate.getForObject(url, AttendanceDTO[].class);
            List<AttendanceDTO> attendanceList = Arrays.asList(response);

            // Pass data to model
            model.addAttribute("attendanceList", attendanceList);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to load attendance records: " + e.getMessage());
        }

        return "attendance/select"; // Thymeleaf template name
    }
//excel list





}
