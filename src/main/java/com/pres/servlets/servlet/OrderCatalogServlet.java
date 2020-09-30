package com.pres.servlets.servlet;

import com.pres.database.repository.impl.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderCatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("orders", OrderRepository.getInstance().findAllOrders());
        req.getRequestDispatcher("/WEB-INF/view/admin_menu.jsp").forward(req, resp);
    }
}
