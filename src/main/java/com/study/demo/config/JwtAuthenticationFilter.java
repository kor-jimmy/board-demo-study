package com.study.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtConfig jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        if (
            requestURI.contains("/api/v1/member")
        ) {
            String token = jwtTokenProvider.resolveToken(httpServletRequest);
            if (token != null
                    && jwtTokenProvider.validateToken(token)) {
                String account = jwtTokenProvider.resolveAccount(token);
                if (account.equals(getRequestedAccount(httpServletRequest))) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "옳바르지 못한 접근입니다.");
                }
            } else {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private String getRequestedAccount(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String[] uriSegments = requestURI.split("/");
        return uriSegments[uriSegments.length - 1];
    }
}