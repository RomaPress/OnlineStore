package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrderCatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("orders" ,OrderRepository.getInstance().findAllOrders());
        req.getRequestDispatcher("/view/admin/admin_menu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("order_id"));
        HttpSession session = req.getSession();
        session.setAttribute("order", OrderRepository.getInstance().findOrderById(id));
        resp.sendRedirect(req.getContextPath() + "/changeOrder");
    }
}
