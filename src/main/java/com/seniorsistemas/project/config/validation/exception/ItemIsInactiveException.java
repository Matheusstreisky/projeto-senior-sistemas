package com.seniorsistemas.project.config.validation.exception;

public class ItemIsInactiveException extends RuntimeException {

    public ItemIsInactiveException() {
        super("Item is inactive.");
    }
}
