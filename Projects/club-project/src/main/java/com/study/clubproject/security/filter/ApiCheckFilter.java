package com.study.clubproject.security.filter;

import com.study.clubproject.security.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {
    private AntPathMatcher antPathMatcher;
    private String pattern;
    private JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;
    }

    private boolean checkAuthHeader(HttpServletRequest request) {
        boolean checkResult = false;

        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) { // 일반 : Basic, JWT : Bearer
            log.info("Authorization Exist : " + authHeader);

            try {
                String email = jwtUtil.validateAndExtract(authHeader.substring(7));

                log.info("Vallidate Result : " + email);

                checkResult = email.length() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        return checkResult;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Requested URI : " + request.getRequestURI());
        log.info(antPathMatcher.match(pattern, request.getRequestURI()));

        if(antPathMatcher.match(pattern, request.getRequestURI())) {
            log.info("Api Check Filter...");

            boolean checkHeader = checkAuthHeader(request);

            if(checkHeader) {
                filterChain.doFilter(request, response);
                return;
            }else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                jsonObject.put("code", "403");
                jsonObject.put("message", message);

                PrintWriter output = response.getWriter();
                output.print(jsonObject);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}