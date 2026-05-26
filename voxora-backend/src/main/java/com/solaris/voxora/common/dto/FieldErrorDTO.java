package com.solaris.voxora.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldErrorDTO {

    private String field;

    private String message;
}