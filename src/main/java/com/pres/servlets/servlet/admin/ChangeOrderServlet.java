package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("update")) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("order",  OrderRepository.getInstance().findOrderById(id));
            req.getRequestDispatcher("/WEB-INF/view/admin/order_info.jsp").forward(req, resp);
        }
    }
}
