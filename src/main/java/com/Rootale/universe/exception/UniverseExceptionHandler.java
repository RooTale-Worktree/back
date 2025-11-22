package com.Rootale.universe.exception;

import com.Rootale.universe.dto.ErrorResponse;
import com.Rootale.universe.service.UniverseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class UniverseExceptionHandler {

    @ExceptionHandler(UniverseService.UniverseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUniverseNotFound(UniverseService.UniverseNotFoundException ex) {
        ErrorResponse error = ErrorResponse.of(
                "NOT_FOUND",
                ex.getMessage(),
                Map.of("status", 404)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = ErrorResponse.of(
                "INTERNAL_SERVER_ERROR",
                "서버 오류가 발생했습니다",
                Map.of("details", ex.getMessage())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}