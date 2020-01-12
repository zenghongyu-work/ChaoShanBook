package com.example.controller.exception;

import com.alibaba.fastjson.JSONObject;
import com.example.controller.common.Result;
import com.example.domain.execption.BusinessException;
import com.example.domain.execption.NotFoundException;
import com.example.domain.execption.UnauthorizedException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Result.builder()
                            .code(0)
                            .msg(businessException.getMessage())
                            .build());
        } else if (e instanceof UnauthorizedException) {
            UnauthorizedException businessException = (UnauthorizedException) e;
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Result.builder()
                            .code(0)
                            .msg(businessException.getMessage())
                            .build());
        } else if (e instanceof NotFoundException) {
            NotFoundException businessException = (NotFoundException) e;
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Result.builder()
                            .code(0)
                            .msg(businessException.getMessage())
                            .build());
        }
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Result.builder()
                        .code(0)
                        .msg(e.getMessage())
                        .build());
    }
}
