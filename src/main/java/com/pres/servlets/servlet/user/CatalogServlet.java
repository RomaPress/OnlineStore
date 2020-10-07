package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.ProductRepository;
import com.pres.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CatalogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products",  ProductRepository.getInstance().findAllProduct());
        req.getRequestDispatcher("/view/user/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        final int id = Integer.parseInt(req.getParameter("id"));
        final int amount = Integer.parseInt(req.getParameter("amount"));
        HttpSession session = req.getSession();



        Product product = ProductRepository.getInstance()
                .findProductByIdWithCurrentAmount(id, amount);

        @SuppressWarnings("unchecked")
        Map<Integer, Product> map = (Map<Integer, Product>) session.getAttribute("selectedProduct");
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(product.getId(), product);
        session.setAttribute("selectedProduct", map);
    }
}
