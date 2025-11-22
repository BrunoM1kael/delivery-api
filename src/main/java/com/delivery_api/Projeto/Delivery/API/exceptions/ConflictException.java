package com.delivery_api.Projeto.Delivery.API.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}