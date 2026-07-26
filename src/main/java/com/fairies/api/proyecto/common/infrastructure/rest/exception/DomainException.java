package com.fairies.api.proyecto.common.infrastructure.rest.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
