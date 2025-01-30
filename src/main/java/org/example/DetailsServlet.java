package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Transaction;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/details")
public class DetailsServlet extends HttpServlet {
    final String passwd = "123456";

    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        context.log(formattedTime + " [DetailsServlet] doGet");

        var session = req.getSession(false);
        if(session==null){
            resp.getWriter().println("Not authorized");
            return;
        }
        resp.getWriter().println("Expenses: ");
        for (Transaction e : (List<Transaction>) context.getAttribute("expenses")) {
            resp.getWriter().println(String.format("- %s(%d)", e.getName(), e.getSum()));
        }
        resp.getWriter().println("\n");
    }
}
