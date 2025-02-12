package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//@WebFilter("/*")
public class LoginFilter implements Filter {


    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var context = servletRequest.getServletContext();
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);
        String requestURI = req.getRequestURI();

        if (requestURI.endsWith("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (session == null) {
            String redirectUrl = req.getRequestURI();
            req.getSession().setAttribute("redirectUrl", redirectUrl);
            resp.sendRedirect(req.getContextPath() + "/login");
        } else filterChain.doFilter(servletRequest, servletResponse);
    }
}
