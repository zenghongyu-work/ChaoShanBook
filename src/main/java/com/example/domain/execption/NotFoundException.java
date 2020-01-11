package com.example.domain.execption;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("NOT FOUND");
    }
}
