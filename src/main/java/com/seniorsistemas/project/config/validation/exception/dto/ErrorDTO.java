package com.seniorsistemas.project.config.validation.exception.dto;

public record ErrorDTO(String message) {

    public ErrorDTO(String message) {
        this.message = message;
    }
}
