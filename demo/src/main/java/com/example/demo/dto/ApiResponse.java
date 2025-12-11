package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiResponse<T> {

    private boolean success;           // 请求是否成功
    private T data;                    // 成功时返回的数据

    // 下面这几个主要用于错误时
    private String errorCode;          // 业务错误码，比如 VALIDATION_ERROR, STUDENT_NOT_FOUND
    private String message;            // 描述信息
    private String path;               // 请求路径：/students, /courses/1
    private LocalDateTime timestamp;   // 时间戳
    private Map<String, String> errors; // 字段级错误（Validation 用）

    // 私有构造函数，统一从静态方法创建
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

    // ✅ 成功返回：最常用
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

    // ✅ 成功 + 自定义 message（用不用随你）
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

    // ❌ 失败：用于各种业务错误/系统错误
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

    // getter（可以不写 setter，让返回值只读）
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
