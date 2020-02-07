package com.example.domain.execption;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token已失效");
    }
}
