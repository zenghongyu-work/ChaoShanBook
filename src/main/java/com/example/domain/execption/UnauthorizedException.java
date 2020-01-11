package com.example.domain.execption;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("请先登录");
    }
}
