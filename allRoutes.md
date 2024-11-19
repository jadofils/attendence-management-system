# API Routes Documentation

## Students Routes

### Basic CRUD Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get student by ID |
| POST | `/api/students` | Create new student |
| PUT | `/api/students/{id}` | Update student |
| DELETE | `/api/students/{id}` | Delete student |

### Search Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/students/email/{email}` | Find student by email |
| GET | `/api/students/program/{programId}` | Get all students in a program |
| GET | `/api/students/course/{courseId}` | Get all students in a course |
| GET | `/api/students/search?name={name}` | Search students by name |

### Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/students/count` | Get total number of students |
| GET | `/api/students/program/{programId}/count` | Count students in a program |
| GET | `/api/students/attendance/summary/{studentId}` | Get attendance summary for student |

## Programs Routes

### Basic CRUD Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/programs` | Get all programs |
| GET | `/api/programs/{id}` | Get program by ID |
| POST | `/api/programs` | Create new program |
| PUT | `/api/programs/{id}` | Update program |
| DELETE | `/api/programs/{id}` | Delete program |

### Search Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/programs/name/{name}` | Find program by name |
| GET | `/api/programs/search?keyword={keyword}` | Search programs by keyword |

### Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/programs/count` | Get total number of programs |
| GET | `/api/programs/{id}/students/count` | Count students in program |
| GET | `/api/programs/{id}/courses/count` | Count courses in program |

## Courses Routes

### Basic CRUD Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/courses` | Get all courses |
| GET | `/api/courses/{id}` | Get course by ID |
| POST | `/api/courses` | Create new course |
| PUT | `/api/courses/{id}` | Update course |
| DELETE | `/api/courses/{id}` | Delete course |

### Search Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/courses/program/{programId}` | Get courses by program |
| GET | `/api/courses/search?name={name}` | Search courses by name |
| GET | `/api/courses/credits/{credits}` | Find courses by credits |

### Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/courses/count` | Get total number of courses |
| GET | `/api/courses/program/{programId}/count` | Count courses in program |
| GET | `/api/courses/{id}/students/count` | Count students in course |

## Classes Routes

### Basic CRUD Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/classes` | Get all classes |
| GET | `/api/classes/{id}` | Get class by ID |
| POST | `/api/classes` | Create new class |
| PUT | `/api/classes/{id}` | Update class |
| DELETE | `/api/classes/{id}` | Delete class |

### Search Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/classes/course/{courseId}` | Get classes by course |
| GET | `/api/classes/instructor/{instructorId}` | Get classes by instructor |
| GET | `/api/classes/code/{classCode}` | Find class by code |

### Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/classes/count` | Get total number of classes |
| GET | `/api/classes/{id}/attendance/summary` | Get attendance summary for class |
| GET | `/api/classes/{id}/students/count` | Count students in class |

## Attendance Routes

### Basic CRUD Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/attendance` | Get all attendance records |
| GET | `/api/attendance/{id}` | Get attendance record by ID |
| POST | `/api/attendance` | Create new attendance record |
| PUT | `/api/attendance/{id}` | Update attendance record |
| DELETE | `/api/attendance/{id}` | Delete attendance record |

### Search Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/attendance/student/{studentId}` | Get attendance by student |
| GET | `/api/attendance/class/{classId}` | Get attendance by class |
| GET | `/api/attendance/date/{date}` | Get attendance by date |
| GET | `/api/attendance/status/{status}` | Get attendance by status |

### Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/attendance/count` | Get total attendance records |
| GET | `/api/attendance/student/{studentId}/summary` | Get student attendance summary |
| GET | `/api/attendance/class/{classId}/summary` | Get class attendance summary |
| GET | `/api/attendance/date-range?start={start}&end={end}` | Get attendance within date range |

## Users Routes

### Basic CRUD Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Search Operations
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/users/username/{username}` | Find user by username |
| GET | `/api/users/role/{role}` | Get users by role |
| GET | `/api/users/search?keyword={keyword}` | Search users |

### Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/users/count` | Get total number of users |
| GET | `/api/users/role/{role}/count` | Count users by role |

## Advanced Analytics Routes

### Program Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/analytics/programs/most-popular` | Get most popular programs |
| GET | `/api/analytics/programs/completion-rate` | Get program completion rates |

### Course Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/analytics/courses/most-attended` | Get most attended courses |
| GET | `/api/analytics/courses/success-rate` | Get course success rates |

### Attendance Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/analytics/attendance/daily-summary` | Get daily attendance summary |
| GET | `/api/analytics/attendance/monthly-summary` | Get monthly attendance summary |
| GET | `/api/analytics/attendance/yearly-summary` | Get yearly attendance summary |

### Student Analytics
| Method | Route | Description |
|--------|-------|-------------|
| GET | `/api/analytics/students/attendance-rate` | Get student attendance rates |
| GET | `/api/analytics/students/performance-summary` | Get student performance summary |