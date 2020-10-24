package com.pres.servlets.servlet.admin;

import com.pres.constants.ErrorMessage;
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
 * This servlet is responsible for order processing. You can perform next
 * actions: see order, delete product from order, change order status and invoice number.
 *
 * @see HttpServlet
 */
public class ChangeOrderServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
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
        req.setAttribute(ServletContent.STATUS, list);
        req.getRequestDispatcher(Path.PATH_TO_ORDER_INFO_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = getOrder(session);

        if (req.getParameterMap().containsKey(ServletContent.DELETE)) {
            int productId = Integer.parseInt(req.getParameter(ServletContent.PRODUCT_ID));
            deleteProduct(req, resp, order, productId);
            refreshOrder(req, resp, session, productId);
        }
        if (req.getParameterMap().containsKey(ServletContent.UPDATE)) {
            String status = req.getParameter(ServletContent.STATUS);
            String invoiceNumber = req.getParameter(ServletContent.INVOICE_NUMBER);
            if (status != null) {
                order.setStatus(status);
                refreshStatus(req, resp, order);
            }
            if (invoiceNumber != null) {
                order.setInvoiceNumber(invoiceNumber);
                refreshInvoiceNumber(req, resp, order);
            }
            int order_id = order.getId();
            refreshOrder(req, resp, session, order_id);
        }
        doGet(req, resp);
    }

    private Order getOrder(HttpSession session) {
        return (Order) session.getAttribute(ServletContent.ORDER);
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp, Order order, int productId) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().deleteProductFromOrder(order, productId);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_DELETE_PRODUCT, e);
            handling(req, resp, ErrorMessage.ERR_DELETE_PRODUCT);
        }
    }

    private void refreshOrder(HttpServletRequest req, HttpServletResponse resp, HttpSession session, int order_id) throws ServletException, IOException {
        Order order = new Order();
        try {
            order = OrderRepository.getInstance().findOrderById(order_id);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_UPDATE_ORDER, e);
            handling(req, resp, ErrorMessage.ERR_UPDATE_ORDER);
        }
        session.setAttribute(ServletContent.ORDER, order);
    }

    private void refreshInvoiceNumber(HttpServletRequest req, HttpServletResponse resp, Order order) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().updateInvoiceNumber(order);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_UPDATE_ORDER_INVOICE_NUMBER, e);
            handling(req, resp, ErrorMessage.ERR_UPDATE_ORDER_INVOICE_NUMBER);
        }
    }

    private void refreshStatus(HttpServletRequest req, HttpServletResponse resp, Order order) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().updateStatus(order);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_UPDATE_ORDER_STATUS, e);
            handling(req, resp, ErrorMessage.ERR_UPDATE_ORDER_STATUS);
        }
    }
}