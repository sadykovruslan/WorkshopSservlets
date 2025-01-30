package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Transaction;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExpensesServlet extends HttpServlet {

    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        context.log(formattedTime + "[ExpensesServlet] doGet");

        var expenses = new ArrayList<Transaction>((List)context.getAttribute("expenses"));

        int freeMoney = (int)context.getAttribute("freeMoney");
        for (var k: req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k));
            freeMoney -=value;
            expenses.add(new Transaction(k, value));
        }

        context.setAttribute("freeMoney", freeMoney);
        context.setAttribute("expenses", expenses);
        resp.getWriter().println("Transaction were added");
    }
}
