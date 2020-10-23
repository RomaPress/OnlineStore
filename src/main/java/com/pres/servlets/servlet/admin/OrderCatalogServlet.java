package com.pres.servlets.servlet.admin;

import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.database.repository.impl.OrderRepository;
import com.pres.exception.DBException;
import com.pres.model.Order;
import com.pres.servlets.ErrorMessageHandler;
import com.pres.servlets.Internationalize;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


/**
 * This servlet is responsible for orders processing. You can perform next
 * actions: see order list, redirect for updating order.
 *
 * @see HttpServlet
 */
public class OrderCatalogServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(OrderCatalogServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ServletContent.ORDERS, getOrder(req, resp));
        req.getRequestDispatcher(Path.PATH_TO_ADMIN_MENU_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(ServletContent.ORDER_ID));
        HttpSession session = req.getSession();
        session.setAttribute(ServletContent.ORDER, getOrderById(req, resp, id));
        resp.sendRedirect(req.getContextPath() + Path.URL_TO_CHANGE_ORDER_PAGE);
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
