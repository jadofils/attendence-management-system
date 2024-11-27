package com.codealpha.attendance.service.schoolService;

import com.codealpha.attendance.model.SchoolClass;
import java.util.List;

public interface SchoolClassService {
    List<SchoolClass> getAllClasses();

     SchoolClass createClass(Long userId, Long courseId, SchoolClass schoolClass);
     void deleteScheduled(Long userId, Long courseId);
     public SchoolClass updateClass(Long userId,Long classId,SchoolClass updatedSchoolClass);
     long count();
     SchoolClass getClassById(Long classid);


}
