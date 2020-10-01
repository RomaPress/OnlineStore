package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.model.Product;
import com.pres.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class BasketServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, Product> map = (Map<Integer, Product>) session.getAttribute("selectedProduct");

        req.setAttribute("selectedProduct", new ArrayList<>(map.values()));
        req.getRequestDispatcher("/WEB-INF/view/user/basket.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, Product> map = (Map<Integer, Product>) session.getAttribute("selectedProduct");

        if (req.getParameterMap().containsKey("delete")) {
            final int id = Integer.parseInt(req.getParameter("deleteId"));

            map.remove(id);
            session.setAttribute("selectedProduct", map);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey("order")) {
            User user = (User) session.getAttribute("currentUser");
            OrderRepository.getInstance().doOrder(map, user);
        }
    }
}
