package com.seniorsistemas.project.config.validation.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {

    public NotFoundException(UUID id) {
        super("No record was found with the ID " + id);
    }
}
