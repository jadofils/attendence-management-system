package com.codealpha.attendance.service.schoolService;

import com.codealpha.attendance.dto.SchoolClassDTO;
import com.codealpha.attendance.model.SchoolClass;
import com.codealpha.attendance.model.User;

import java.util.List;

public interface SchoolClassService {
    List<SchoolClass> getAllClasses();

    public SchoolClass createClass(Long userId, SchoolClassDTO schoolClassDTO);
         void deleteScheduled(Long userId, Long courseId);
     public SchoolClass updateClass(Long userId, Long classId, SchoolClassDTO schoolClassDTO);
     long count();
     SchoolClass getClassById(Long classid);
List<User> getAllInstructors();

}
