package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.ProductRepository;
import com.pres.exeption.DBException;
import com.pres.model.Product;
import com.pres.servlets.ErrorCatchable;
import com.pres.servlets.Internationalize;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeProductServlet extends HttpServlet implements ErrorCatchable, Internationalize {
    private static final Logger LOG = Logger.getLogger(ChangeProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("update")) {
            int productId = Integer.parseInt(req.getParameter("product_id"));
            Product product = getProduct(req, resp, productId);
            req.setAttribute("product", product);
            req.getRequestDispatcher("/jsp/admin/product_info.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("product_id"));

        if (req.getParameterMap().containsKey("change")) {
            Product product = extractProduct(req);
            updateProduct(req, resp, product);
            resp.sendRedirect(req.getContextPath() + "/changeProduct?product_id="+productId+"&update=true");
        } else if (req.getParameterMap().containsKey("delete")) {
            deleteProduct(req, resp, productId);
            resp.sendRedirect(req.getContextPath() + "/controlProduct");
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp, Product product) throws ServletException, IOException {
        try {
            ProductRepository.getInstance().updateProduct(product);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
        Product product = new Product.Builder().setId(id).build();
        try {
            ProductRepository.getInstance().deleteProduct(product);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }

    private Product getProduct(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
        Product product = new Product.Builder().build();
        try {
            product = ProductRepository.getInstance().findProductById(id);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return product;
    }

    private Product extractProduct(HttpServletRequest req) {
        return new Product.Builder()
                .setId(Integer.parseInt(req.getParameter("product_id")))
                .setAmount(Integer.parseInt(req.getParameter("amount")))
                .setPrice(Double.parseDouble(req.getParameter("price")))
                .build();
    }
}
