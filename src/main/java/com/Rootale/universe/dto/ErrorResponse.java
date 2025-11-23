package com.Rootale.universe.dto;

import java.util.Map;

public record ErrorResponse(
        String error,
        String message,
        Map<String, Object> details
) {
    public static ErrorResponse of(String error, String message) {
        return new ErrorResponse(error, message, Map.of());
    }

    public static ErrorResponse of(String error, String message, Map<String, Object> details) {
        return new ErrorResponse(error, message, details);
    }
}