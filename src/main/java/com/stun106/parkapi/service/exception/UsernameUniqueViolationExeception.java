package com.stun106.parkapi.service.exception;

public class UsernameUniqueViolationExeception extends RuntimeException {
    public UsernameUniqueViolationExeception(String message) {
        super(message);
    }
}
