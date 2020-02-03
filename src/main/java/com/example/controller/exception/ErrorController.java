package com.example.controller.exception;

import com.example.controller.common.Result;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Api(value = "filter错误处理")
public class ErrorController extends BasicErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    @RequestMapping
    public ResponseEntity error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = HttpStatus.OK;
        if (!Strings.isNullOrEmpty((String) body.get("message")) && body.get("message").equals("请先登录")) {
            return ResponseEntity.status(status)
                    .body(Result.builder()
                            .code(0)
                            .msg((String) body.get("message"))
                            .build());
        } else if (!Strings.isNullOrEmpty((String) body.get("message")) && body.get("message").equals("好久不见哟，重新登录下吧")) {
            return ResponseEntity.status(status)
                    .body(Result.builder()
                            .code(11011)
                            .msg((String) body.get("message"))
                            .build());
        }

        return ResponseEntity.status(status)
                .body(Result.builder()
                        .code(0)
                        .msg((String) body.get("message"))
                        .build());
    }

    @Override
    public String getErrorPath() {
        return "error/error";
    }

}