package com.example.domain.execption;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
