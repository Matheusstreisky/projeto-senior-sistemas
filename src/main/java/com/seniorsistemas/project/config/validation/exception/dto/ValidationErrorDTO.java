package com.seniorsistemas.project.config.validation.exception.dto;

import org.springframework.validation.FieldError;

public record ValidationErrorDTO(String field, String message) {

    public ValidationErrorDTO(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
