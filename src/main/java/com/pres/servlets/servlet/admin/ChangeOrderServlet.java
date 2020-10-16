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


public class ChangeOrderServlet extends HttpServlet implements ErrorCatchable {
    private static final Logger LOG = Logger.getLogger(ChangeOrderServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> list = null;
        try {
            list = OrderRepository.getInstance().selectAllStatus();
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        req.setAttribute("status", list);
        req.getRequestDispatcher("/jsp/admin/order_info.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = getOrder(session);

        if (req.getParameterMap().containsKey("delete")) {
            int productId = Integer.parseInt(req.getParameter("product_id"));

            deleteProduct(req, resp, order, productId);
            refreshOrder(req, resp, session, order);
        }
        if (req.getParameterMap().containsKey("update")) {
            String status = req.getParameter("status");
            String invoiceNumber = req.getParameter("invoiceNumber");
            if (status != null) {
                order.setStatus(status);
                refreshStatus(req, resp, order);
            }
            if (invoiceNumber != null) {
                order.setInvoiceNumber(invoiceNumber);
                refreshInvoiceNumber(req, resp, order);
            }
            refreshOrder(req, resp, session, order);
        }
        doGet(req, resp);
    }

    private Order getOrder(HttpSession session) {
        return (Order) session.getAttribute("order");
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp, Order order, int productId) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().deleteProductFromOrder(order, productId);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }

    private void refreshOrder(HttpServletRequest req, HttpServletResponse resp, HttpSession session, Order order) throws ServletException, IOException {
        try {
            order = OrderRepository.getInstance().findOrderById(order.getId());
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        session.setAttribute("order", order);
    }

    private void refreshInvoiceNumber(HttpServletRequest req, HttpServletResponse resp, Order order) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().updateInvoiceNumber(order);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }

    private void refreshStatus(HttpServletRequest req, HttpServletResponse resp, Order order) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().updateStatus(order);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }
}