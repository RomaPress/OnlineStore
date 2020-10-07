package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.model.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class ChangeOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/admin/order_info.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("delete")) {
            int productId = Integer.parseInt(req.getParameter("product_id"));

            HttpSession session = req.getSession();
            Order order = (Order) session.getAttribute("order");

            deleteProduct(order, productId);
            refreshOrder(session, order);
        }
        doGet(req, resp);
    }


    private void deleteProduct(Order order, int productId) {
        OrderRepository.getInstance().deleteProductFromOrder(order, productId);
    }

    private void refreshOrder(HttpSession session, Order order){
        order = OrderRepository.getInstance().findOrderById(order.getId());
        session.setAttribute("order", order);
    }
}