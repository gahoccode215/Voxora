package com.solaris.voxora.common.exception;

import com.solaris.voxora.common.dto.ApiResponseDTO;
import com.solaris.voxora.common.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleAppException(AppException ex) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(
                        ApiResponseDTO.error(
                                ex.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        List<FieldErrorDTO> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> FieldErrorDTO.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();

        return ResponseEntity
                .badRequest()
                .body(
                        ApiResponseDTO.error(
                                "Validation failed",
                                errors
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<?>> handleException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponseDTO.error(
                                "Internal server error",
                                null
                        )
                );
    }
}