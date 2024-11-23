package com.codealpha.attendance.service.schoolService;

import com.codealpha.attendance.model.SchoolClass;
import java.util.List;

public interface SchoolClassService {
    List<SchoolClass> getAllClasses();

    SchoolClass createClass(Long userId, Long courseId, SchoolClass schoolClass);
    void deleteScheduled(Long userId, Long courseId);


}
