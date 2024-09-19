package com.seniorsistemas.project.config.validation.exception;

public class ItemIsInactive extends RuntimeException {

    public ItemIsInactive() {
        super("Item is inactive.");
    }
}
