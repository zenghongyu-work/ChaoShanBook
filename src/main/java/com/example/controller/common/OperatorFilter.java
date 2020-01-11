package com.example.controller.common;

import com.example.domain.execption.UnauthorizedException;
import com.example.infrastructure.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.example.domain.common.Constant.TOKEN;

@WebFilter
@Order(5)
public class OperatorFilter implements Filter {

    public static final List<String> BLACK_PATH = Arrays.asList("PUT /chaoshanbook/user/nickname",
            "POST /chaoshanbook/video");

    @Autowired
    private Operator operator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (!BLACK_PATH.contains(httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            String token = httpServletRequest.getHeader(TOKEN);
            if (StringUtils.isBlank(token)) {
                throw new UnauthorizedException();
            }

            Claims claims = JwtUtils.parseJWT(token);
            operator.setId(Integer.parseInt(claims.getSubject()));
            chain.doFilter(request, response);
        }
    }
}
