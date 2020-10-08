package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.model.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class ChangeOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> list = OrderRepository.getInstance().selectAllStatus();
        req.setAttribute("status", list);
        req.getRequestDispatcher("/view/admin/order_info.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Order order = getOrder(session);

        if (req.getParameterMap().containsKey("delete")) {
            int productId = Integer.parseInt(req.getParameter("product_id"));

            deleteProduct(order, productId);
            refreshOrder(session, order);
        }
        if (req.getParameterMap().containsKey("update")) {
            String status = req.getParameter("status");
            String invoiceNumber = req.getParameter("invoiceNumber");
            if (status != null){
                order.setStatus(status);
                OrderRepository.getInstance().updateStatus(order);
            }
            if(invoiceNumber !=null){
                order.setInvoiceNumber(invoiceNumber);
                OrderRepository.getInstance().updateInvoiceNumber(order);
            }
            refreshOrder(session, order);
        }
        doGet(req, resp);
    }
    public Order getOrder(HttpSession session){
        return (Order) session.getAttribute("order");
    }

    private void deleteProduct(Order order, int productId) {
        OrderRepository.getInstance().deleteProductFromOrder(order, productId);
    }

    private void refreshOrder(HttpSession session, Order order){
        order = OrderRepository.getInstance().findOrderById(order.getId());
        session.setAttribute("order", order);
    }
}