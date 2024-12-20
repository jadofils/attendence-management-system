// package com.codealpha.attendance.exception;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;

// import java.time.LocalDateTime;
// import java.util.HashMap;
// import java.util.Map;

// @ControllerAdvice
// public class GlobalExceptionHandler {

//     @ExceptionHandler(IllegalArgumentException.class)
//     public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
//         Map<String, Object> errorResponse = new HashMap<>();
//         errorResponse.put("timestamp", LocalDateTime.now());
//         errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
//         errorResponse.put("error", "Bad Request");
//         errorResponse.put("message", ex.getMessage());
//         return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//     }

//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
//         Map<String, Object> errorResponse = new HashMap<>();
//         errorResponse.put("timestamp", LocalDateTime.now());
//         errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//         errorResponse.put("error", "Internal Server Error");
//         errorResponse.put("message", "An unexpected error occurred.");
//         return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//     }
// }
