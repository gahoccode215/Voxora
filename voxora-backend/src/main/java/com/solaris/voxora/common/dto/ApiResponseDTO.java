package com.solaris.voxora.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponseDTO<T> {

    private boolean success;

    private String message;

    private T data;

    private Object errors;

    public static <T> ApiResponseDTO<T> success(String message, T data) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .errors(null)
                .build();
    }

    public static <T> ApiResponseDTO<T> error(String message, Object errors) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .errors(errors)
                .build();
    }
}