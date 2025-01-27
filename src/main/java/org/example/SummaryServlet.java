package org.example;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Transaction;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SummaryServlet extends HttpServlet {
    LocalTime currentTime = LocalTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    String formattedTime = currentTime.format(formatter);

    @Override
    public void init(ServletConfig config) throws ServletException {
        var context = config.getServletContext();
        context.log(formattedTime + " [SummaryServlet] init ");
        int salary =Integer.parseInt(context.getInitParameter("salary"));
        int rent =Integer.parseInt(config.getInitParameter("rent"));

        List<Transaction> expense = new ArrayList<>();
        expense.add(new Transaction("rent", rent));
        context.setAttribute("expenses", expense);

        context.setAttribute("freeMoney", salary - rent);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        context.log(formattedTime + " [SummaryServlet] doGet ");

        var session = req.getSession(false);
        if(session==null){
            resp.getWriter().println("Not authorized");
            return;
        }

        req.getRequestDispatcher("/details").include(req, resp);
        resp.getWriter().println("Free money: " + context.getAttribute("freeMoney"));
    }

}
