package com.codealpha.attendance.service.attendanceService;

import com.codealpha.attendance.model.Attendance;
import com.codealpha.attendance.repository.AttendanceRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> getAllAttendance() {
        // Fetch all attendance records from the repository
        return attendanceRepository.findAll();
    }
    @Override
    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance); // Save the attendance to the database
    }

   
    @Override
    public Optional<Attendance> getAttendanceById(Long attendanceId) {
        // Find attendance by ID, will return an Optional
        return attendanceRepository.findById(attendanceId);
    }
}
