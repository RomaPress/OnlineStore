package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.exeption.DBException;
import com.pres.model.Product;
import com.pres.model.User;
import com.pres.servlets.ErrorCatchable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class BasketServlet extends HttpServlet implements ErrorCatchable {
    private static final Logger LOG = Logger.getLogger(BasketServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Integer, Product> map = getSelectedProduct(session);

        req.setAttribute("selectedProduct", new ArrayList<>(map.values()));
        req.getRequestDispatcher("/view/user/basket.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Integer, Product> selectedProduct = getSelectedProduct(session);

        if (req.getParameterMap().containsKey("delete")) {
            final int id = Integer.parseInt(req.getParameter("deleteId"));
            selectedProduct.remove(id);
            setSelectedProduct(session, selectedProduct);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey("order") && selectedProduct.size() != 0) {
            User user = (User) session.getAttribute("currentUser");
            doOrder(req, resp, selectedProduct, user);
            resp.sendRedirect(req.getContextPath() + "/catalog");
        }
    }

    private Map<Integer, Product> getSelectedProduct(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Integer, Product> answer = (Map<Integer, Product>) session.getAttribute("selectedProduct");
        return answer;
    }

    private void setSelectedProduct(HttpSession session, Map<Integer, Product> map) {
        session.setAttribute("selectedProduct", map);
    }

    private void doOrder(HttpServletRequest req, HttpServletResponse resp,
                         Map<Integer, Product> selectedProduct, User user) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().doOrder(selectedProduct, user);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }
}
