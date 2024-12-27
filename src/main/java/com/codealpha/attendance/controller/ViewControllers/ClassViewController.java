package com.codealpha.attendance.controller.ViewControllers;

import com.codealpha.attendance.dto.SchoolClassDTO;

import com.codealpha.attendance.service.schoolService.SchoolClassService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



import java.util.List;

@Controller
public class ClassViewController {

    @Autowired
    private SchoolClassService schoolClassService;
   


    @GetMapping("/classes/select")
public String viewClasses(Model model) {
    List<SchoolClassDTO> classDTOs = schoolClassService.getAllClasses()
            .stream()
            .map(schoolClass -> {
                SchoolClassDTO dto = new SchoolClassDTO();
                dto.setClassId(schoolClass.getClassId());
                dto.setClassCode(schoolClass.getClassCode());
                dto.setClassSchedule(schoolClass.getClassSchedule());
                dto.setCourseId(schoolClass.getCourse().getCourseId());
                dto.setCourseName(schoolClass.getCourse().getCourseName());
                dto.setProgramName(schoolClass.getProgram().getProgramName());
                dto.setInstructorName(schoolClass.getInstructor().getUsername());
                dto.setProgramId(schoolClass.getProgram().getProgramId()); // Accept programId
                dto.setInstructorId(schoolClass.getInstructor().getUserId()); // Map Instructor ID
                return dto;
            })
            .toList();

    model.addAttribute("classes", classDTOs);
    return "classes/select";
}
}
