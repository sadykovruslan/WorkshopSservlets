package org.example;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Transaction;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/incomes/add")
public class IncomesServlet extends HttpServlet {

    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        context.log(formattedTime + "[IncomesServlet] doGet");

        List<Transaction> incomes = new ArrayList<>((List)context.getAttribute("incomes"));

        int freeMoney = (int)context.getAttribute("freeMoney");
        for(var k : req.getParameterMap().keySet()){
            int value = Integer.parseInt(req.getParameter(k));
            freeMoney +=value;
            incomes.add(new Transaction(k, value));
        }

        context.setAttribute("freeMoney", freeMoney);
        context.setAttribute("incomes", incomes);
        resp.getWriter().println("Incomes were added");
        resp.sendRedirect("/summary");
    }
}
