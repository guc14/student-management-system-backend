package com.guc.studentmanagement.exception;

import com.guc.studentmanagement.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) Handle validation errors triggered by @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // Collect all field-level validation errors: field -> message
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.putIfAbsent(field, message);
        }

        ApiResponse<Void> body = ApiResponse.error(
                "VALIDATION_ERROR",        // errorCode
                "Validation failed",       // message
                request.getRequestURI(),   // path
                errors                     // field-level errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 2)  StudentNotFoundException
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleStudentNotFound(
            StudentNotFoundException ex,
            HttpServletRequest request) {

        ApiResponse<Void> body = ApiResponse.error(
                "STUDENT_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 2.1)  CourseNotFoundException
    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCourseNotFound(
            CourseNotFoundException ex,
            HttpServletRequest request) {

        ApiResponse<Void> body = ApiResponse.error(
                "COURSE_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 3) Fallback exception handler(handles all other unhandled exceptions in the system)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(
            Exception ex,
            HttpServletRequest request) {

        ApiResponse<Void> body = ApiResponse.error(
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
