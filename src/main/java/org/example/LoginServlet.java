package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final String passwd = "123456";

    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        context.log( formattedTime + " [LoginServlet] doGet");
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        context.log( formattedTime + " [LoginServlet] doPost ");
        String password = req.getParameter("password");
        if(password.equals(passwd)){
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(10);
            session.setAttribute("isAuthenticated", true);

            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl != null && !redirectUrl.equals("/login")) {
                resp.sendRedirect(redirectUrl);
            } else resp.sendRedirect(req.getContextPath() + "/summary");
        } else {
            resp.getWriter().println("Wrong password");
        }
    }
}
