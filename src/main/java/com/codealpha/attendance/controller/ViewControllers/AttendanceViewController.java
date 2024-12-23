package com.codealpha.attendance.controller.ViewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.codealpha.attendance.dto.AttendanceDTO;
import java.util.List;
import java.util.Arrays;

@Controller
public class AttendanceViewController {

    @GetMapping("/attendance/select")
    public String getAttendanceView(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        AttendanceDTO[] response = restTemplate.getForObject("http://localhost:8080/api/attendance/all", AttendanceDTO[].class);
        List<AttendanceDTO> attendanceList = Arrays.asList(response);
        model.addAttribute("attendanceList", attendanceList);
        return "attendance/select";
    }
}



