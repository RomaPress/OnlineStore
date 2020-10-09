package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.exeption.DBException;
import com.pres.model.Order;
import com.pres.servlets.ErrorCatchable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class OrderCatalogServlet extends HttpServlet implements ErrorCatchable {
    private static final Logger LOG = Logger.getLogger(OrderCatalogServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("orders", getOrder(req, resp));
        req.getRequestDispatcher("/view/admin/admin_menu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("order_id"));
        HttpSession session = req.getSession();
        session.setAttribute("order", getOrderById(req, resp, id));
        resp.sendRedirect(req.getContextPath() + "/changeOrder");
    }

    private List<Order> getOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = null;
        try {
            orders = OrderRepository.getInstance().findAllOrders();
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return orders;
    }

    private Order getOrderById(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
        Order order = null;
        try {
            order = OrderRepository.getInstance().findOrderById(id);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return order;
    }
}
