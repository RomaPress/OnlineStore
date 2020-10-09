package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.ProductRepository;
import com.pres.exeption.DBException;
import com.pres.model.Product;
import com.pres.servlets.ErrorCatchable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogServlet extends HttpServlet implements ErrorCatchable {
    private static final Logger LOG = Logger.getLogger(CatalogServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = getProduct(req, resp);
        req.setAttribute("products", products);
        req.getRequestDispatcher("/view/user/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        final int id = Integer.parseInt(req.getParameter("id"));
        final int amount = Integer.parseInt(req.getParameter("amount"));

        Product product = findProductByIdWithNewAmount(req, resp, id, amount);
        Map<Integer, Product> selectedProduct = getSelectedProduct(session);
        selectedProduct.put(product.getId(), product);
        session.setAttribute("selectedProduct", selectedProduct);
    }

    private List<Product> getProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = null;
        try {
            products = ProductRepository.getInstance().findAllProduct();
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return products;
    }

    private Product findProductByIdWithNewAmount(HttpServletRequest req, HttpServletResponse resp, int id, int amount) throws ServletException, IOException {
        Product product = null;
        try {
            product = ProductRepository.getInstance().findProductByIdWithNewAmount(id, amount);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return product;
    }

    private  Map<Integer, Product> getSelectedProduct(HttpSession session){
        @SuppressWarnings("unchecked")
        Map<Integer, Product> map = (Map<Integer, Product>) session.getAttribute("selectedProduct");
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }
}
