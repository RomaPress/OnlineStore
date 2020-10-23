package com.pres.servlets.servlet.admin;

import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.database.repository.impl.ProductRepository;
import com.pres.exception.DBException;
import com.pres.model.Product;
import com.pres.servlets.ErrorMessageHandler;
import com.pres.servlets.Internationalize;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeProductServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(ChangeProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey(ServletContent.UPDATE)) {
            int productId = Integer.parseInt(req.getParameter(ServletContent.PRODUCT_ID));
            Product product = getProduct(req, resp, productId);
            req.setAttribute(ServletContent.PRODUCT, product);
            req.getRequestDispatcher(Path.PATH_TO_PRODUCT_INFO_PAGE).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter(ServletContent.PRODUCT_ID));

        if (req.getParameterMap().containsKey(ServletContent.CHANGE)) {
            Product product = extractProduct(req);
            updateProduct(req, resp, product);
            resp.sendRedirect(req.getContextPath() + Path.URL_TO_CHANGE_PRODUCT_PAGE + "?" +
                    ServletContent.PRODUCT_ID + "=" + productId +
                    "&" + ServletContent.UPDATE + "=true");
        } else if (req.getParameterMap().containsKey(ServletContent.DELETE)) {
            deleteProduct(req, resp, productId);
            resp.sendRedirect(req.getContextPath() + Path.URL_TO_CONTROL_PRODUCT_PAGE);
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
                .setId(Integer.parseInt(req.getParameter(ServletContent.PRODUCT_ID)))
                .setAmount(Integer.parseInt(req.getParameter(ServletContent.AMOUNT)))
                .setPrice(Double.parseDouble(req.getParameter(ServletContent.PRICE)))
                .build();
    }
}
