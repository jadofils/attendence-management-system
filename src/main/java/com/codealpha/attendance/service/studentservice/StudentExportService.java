// package com.codealpha.attendance.service.studentservice;

// import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// import org.apache.poi.ss.usermodel.*;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.util.List;
// import java.util.Map;

// @Service
// public class StudentExportService {

//     private final RestTemplate restTemplate;

//     public StudentExportService(RestTemplate restTemplate) {
//         this.restTemplate = restTemplate;
//     }

//     // API URLs
//     private static final String STUDENT_API_URL = "http://localhost:8080/api/students/all";
//     private static final String PROGRAM_API_URL = "http://localhost:8080/api/programs/";
//     private static final String USER_API_URL = "http://localhost:8080/api/users/";
//     private static final String COURSE_API_URL = "http://localhost:8080/api/courses/";

//     public String exportStudentsToExcel() throws IOException {
//         // Fetch data from APIs
//         List<Map<String, Object>> students = restTemplate.getForObject(STUDENT_API_URL, List.class);
//         List<Map<String, Object>> programs = restTemplate.getForObject(PROGRAM_API_URL, List.class);
//         List<Map<String, Object>> users = restTemplate.getForObject(USER_API_URL, List.class);
//         List<Map<String, Object>> courses = restTemplate.getForObject(COURSE_API_URL, List.class);

//         // Create Workbook and Sheet
//         Workbook workbook = new HSSFWorkbook();
//         Sheet sheet = workbook.createSheet("Students");

//         // Create Header Row
//         Row headerRow = sheet.createRow(0);
//         String[] headers = {"ID", "First Name", "Last Name", "Email", "Program", "Course", "User Role"};
//         for (int i = 0; i < headers.length; i++) {
//             Cell cell = headerRow.createCell(i);
//             cell.setCellValue(headers[i]);
//             cell.setCellStyle(getHeaderCellStyle(workbook));
//         }

//         // Populate Rows with Student Data
//         int rowNum = 1;
//         for (Map<String, Object> student : students) {
//             Row row = sheet.createRow(rowNum++);

//             row.createCell(0).setCellValue(student.get("id").toString());
//             row.createCell(1).setCellValue(student.get("firstName").toString());
//             row.createCell(2).setCellValue(student.get("lastName").toString());
//             row.createCell(3).setCellValue(student.get("email").toString());

//             // Get Program Name
//             String programName = programs.stream()
//                     .filter(p -> p.get("id").toString().equals(student.get("programId").toString()))
//                     .findFirst()
//                     .map(p -> p.get("name").toString())
//                     .orElse("N/A");
//             row.createCell(4).setCellValue(programName);

//             // Get Course Name
//             String courseName = courses.stream()
//                     .filter(c -> c.get("id").toString().equals(student.get("courseId").toString()))
//                     .findFirst()
//                     .map(c -> c.get("name").toString())
//                     .orElse("N/A");
//             row.createCell(5).setCellValue(courseName);

//             // Get User Role
//             String userRole = users.stream()
//                     .filter(u -> u.get("id").toString().equals(student.get("userId").toString()))
//                     .findFirst()
//                     .map(u -> u.get("role").toString())
//                     .orElse("N/A");
//             row.createCell(6).setCellValue(userRole);
//         }

//         // Autosize Columns
//         for (int i = 0; i < headers.length; i++) {
//             sheet.autoSizeColumn(i);
//         }

//         // Write to File
//         String filePath = "students.xls";
//         try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//             workbook.write(fileOut);
//         }
//         workbook.close();

//         return filePath;
//     }

//     // Header Cell Style
//     private CellStyle getHeaderCellStyle(Workbook workbook) {
//         CellStyle style = workbook.createCellStyle();
//         Font font = workbook.createFont();
//         font.setBold(true);
//         style.setFont(font);
//         style.setAlignment(HorizontalAlignment.CENTER);
//         style.setVerticalAlignment(VerticalAlignment.CENTER);
//         return style;
//     }
// }
