package com.guc.studentmanagement.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiResponse<T> {

    private boolean success;           //  Indicates whether the request was successful
    private T data;                    // Data returned when the request is successful

    //  The following fields are mainly used for error responses
    private String errorCode;          // Business error code, e.g. VALIDATION_ERROR, STUDENT_NOT_FOUND
    private String message;
    private String path;               // Request path, e.g. /students, /courses/1
    private LocalDateTime timestamp;   //Timestamp
    private Map<String, String> errors; // Field-level validation errors

    //  Private constructor; instances should be created via static factory methods
    private ApiResponse(boolean success,
                        T data,
                        String errorCode,
                        String message,
                        String path,
                        LocalDateTime timestamp,
                        Map<String, String> errors) {
        this.success = success;
        this.data = data;
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    // ✅ Success response (most commonly used)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                data,
                null,
                null,
                null,
                LocalDateTime.now(),
                null
        );
    }

    // ✅ Success response with a custom message
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(
                true,
                data,
                null,
                message,
                null,
                LocalDateTime.now(),
                null
        );
    }

    // ❌ Error response: used for various business errors and system errors
    public static <T> ApiResponse<T> error(String errorCode,
                                           String message,
                                           String path,
                                           Map<String, String> errors) {
        return new ApiResponse<>(
                false,
                null,
                errorCode,
                message,
                path,
                LocalDateTime.now(),
                errors
        );
    }

    // getter
    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
