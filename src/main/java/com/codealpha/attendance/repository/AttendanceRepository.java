package com.codealpha.attendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codealpha.attendance.model.Attendance;
import com.codealpha.attendance.model.AttendanceStatus;
import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.model.Student;
@Repository
// AttendanceRepository.java
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentStudentId(Long studentId);
    List<Attendance> findByAttendedClassClassId(Long classId);
    List<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);
    boolean existsByAttendanceDateAndStudentAndAttendedClass(LocalDate attendanceDate, Student student,
            SchoolClass schoolClass);
    long countByAttendanceStatus(AttendanceStatus present);
}