// package com.codealpha.attendance.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.codealpha.attendance.service.studentservice.StudentExportService;

// import java.io.IOException;

// @RestController
// public class StudentExportController {

//     @Autowired
//     private StudentExportService studentExportService;

//     @GetMapping("/export/students")
//     public String exportStudentsToExcel() {
//         try {
//             String filePath = studentExportService.exportStudentsToExcel();
//             return "Excel file created successfully at: " + filePath;
//         } catch (IOException e) {
//             e.printStackTrace();
//             return "Failed to create Excel file: " + e.getMessage();
//         }
//     }
// }
