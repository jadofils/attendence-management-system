package com.codealpha.attendance.service.attendanceService;



import java.util.List;
import java.util.Optional;

import com.codealpha.attendance.model.Attendance;


public interface AttendanceService {

    List<Attendance> getAllAttendance();
    Attendance saveAttendance(Attendance attendance); // New method for saving attendance

  
    // New method to get attendance by ID
    Optional<Attendance> getAttendanceById(Long attendanceId);

}
