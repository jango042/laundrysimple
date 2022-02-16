package com.jango.laundrysimple.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDenied implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json;charset=UTF-8");
        Map<String, Serializable> map = new HashMap<>();
        map.put("timestamp", LocalDate.now());
        map.put("status", 403);
        map.put("message", "Access Denied, You dont have the permission to view this URL");
        res.setStatus(403);
        res.getWriter().write(map.toString());
    }
}
