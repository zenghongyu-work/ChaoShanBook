package com.example.controller.exception;

import com.alibaba.fastjson.JSONObject;
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
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body(transform(businessException.getMessage()));
        } else if (e instanceof UnauthorizedException) {
            UnauthorizedException businessException = (UnauthorizedException) e;
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(transform(businessException.getMessage()));
        } else if (e instanceof NotFoundException) {
            NotFoundException businessException = (NotFoundException) e;
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(transform(businessException.getMessage()));
        }
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(transform(e.getMessage()));
    }

    public static JSONObject transform(String message) {
        JSONObject object = new JSONObject();
        object.put("error", message);
        return object;
    }
}
