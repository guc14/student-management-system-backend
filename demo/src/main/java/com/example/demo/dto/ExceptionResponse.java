package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ExceptionResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    // ⭐ 新增：字段级错误（Validation 使用）
    private Map<String, String> errors;

    // 构造函数（带 errors）
    public ExceptionResponse(LocalDateTime timestamp,
                             int status,
                             String error,
                             String message,
                             String path,
                             Map<String, String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    // 构造函数（不带 errors）给其他异常使用
    public ExceptionResponse(LocalDateTime timestamp,
                             int status,
                             String error,
                             String message,
                             String path) {
        this(timestamp, status, error, message, path, null);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
