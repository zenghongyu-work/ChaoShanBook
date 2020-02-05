package com.example.controller.common;

import com.example.domain.execption.ExpiredTokenException;
import com.example.domain.execption.UnauthorizedException;
import com.example.infrastructure.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
            "POST /chaoshanbook/video", "POST /chaoshanbook/article",
            "PUT /chaoshanbook/user", "POST /chaoshanbook/user/follow", "DELETE /chaoshanbook/user/follow",
            "GET /chaoshanbook/user/my-followers", "GET /chaoshanbook/user/users-follow",
            "POST /chaoshanbook/video/collect", "POST /chaoshanbook/article/collect",
            "POST /chaoshanbook/comment", "DELETE /chaoshanbook/comment/{id}",
            "GET /chaoshanbook/user/is-follow", "POST /chaoshanbook/user/update/token",
            "POST /chaoshanbook/user/update", "POST /chaoshanbook/user/update/icon");

    @Autowired
    private Operator operator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String token = httpServletRequest.getHeader(TOKEN);
        if (StringUtils.isNotBlank(token)) {
            Claims claims;
            try {
                claims = JwtUtils.parseJWT(token);
            } catch (ExpiredJwtException e) {
                throw new ExpiredTokenException();
            }
            operator.setId(Integer.parseInt(claims.getSubject()));
        }

        if (!BLACK_PATH.contains(httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            if (operator.getId() == null) {
                throw new UnauthorizedException();
            }

            chain.doFilter(request, response);
        }
    }
}
