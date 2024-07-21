package com.ms.security.authserver.config.security;

import com.ms.security.authserver.response.Response;
import com.ms.security.authserver.response.ResponseStatus;
import com.ms.security.authserver.util.CommonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(AuthEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException, ServletException {
        String authTokenHeader = httpServletRequest.getHeader("Authorization");
        if(!(authTokenHeader != null && authTokenHeader.startsWith("Bearer".concat(" ")))) {
            logger.warn(httpServletRequest.getRequestURI().concat("is missing"));
        }

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        Response<Object> response = new Response<>();
        response.setErrorCode(HttpStatus.UNAUTHORIZED.toString());
        response.setMessage("Access Denied");
        response.setStatus(ResponseStatus.FAIL);
        httpServletResponse.getWriter().write(CommonUtil.convertObjectToJson(response));
    }
}
