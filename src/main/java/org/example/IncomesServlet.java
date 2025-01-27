package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/incomes/add")
public class IncomesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        context.log("[IncomesServlet] doGet");

        var incomes = new ArrayList<Transaction>((List)context.getAttribute("incomes"));
        int salary = Integer.parseInt(context.getInitParameter("salary"));
        }
}
