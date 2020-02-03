package com.example.domain.execption;

public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException() {
        super("好久不见哟，重新登录下吧");
    }
}
