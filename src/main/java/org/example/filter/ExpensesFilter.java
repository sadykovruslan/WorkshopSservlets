package org.example.filter;

import jakarta.servlet.*;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ExpensesFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        var context = servletRequest.getServletContext();
        context.log(formattedTime + "[ExpensesFilter] doFilter");

        int freeMoney = (int)context.getAttribute("freeMoney");
        for(var k: servletRequest.getParameterMap().keySet()){
            freeMoney -=Integer.parseInt(servletRequest.getParameter(k));
            if(freeMoney < 0){
                servletResponse.getWriter().println("Not enough money");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
