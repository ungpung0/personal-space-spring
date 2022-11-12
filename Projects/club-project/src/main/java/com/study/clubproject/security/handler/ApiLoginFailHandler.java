package com.study.clubproject.security.handler;

import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiLoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("Login Fail Handler");

        log.info(exception.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=utf-8");

        JSONObject jsonObject = new JSONObject();
        String message = exception.getMessage();
        jsonObject.put("code", "401");
        jsonObject.put("message", message);

        PrintWriter output = response.getWriter();
        output.print(jsonObject);
    }
}
