package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.ProductRepository;
import com.pres.exception.DBException;
import com.pres.model.Product;
import com.pres.servlets.ErrorCatchable;
import com.pres.servlets.Internationalize;
import com.pres.util.Process;
import com.pres.util.sort.ProductSort;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogServlet extends HttpServlet implements ErrorCatchable, Internationalize {
    private static final Logger LOG = Logger.getLogger(CatalogServlet.class);
    private static final int COUNT_PRODUCT_IN_PAGE = 12;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int page = 1;
        if (session.getAttribute("page") != null) {
            page = (int) session.getAttribute("page");
        }
        int sortAlg = ProductSort.SORT_BY_ID;
        if (session.getAttribute("sort") != null) {
            sortAlg = (int) session.getAttribute("sort");
        }
        List<Product> products = ProductSort.sort(getProduct(req, resp), sortAlg);
        List<Product> cutProducts = Process.cutList(products, COUNT_PRODUCT_IN_PAGE, page);
        req.setAttribute("products", cutProducts);
        req.setAttribute("size", products.size());
        req.setAttribute("countProduct", COUNT_PRODUCT_IN_PAGE);
        req.getRequestDispatcher("/jsp/user/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey("previousPage")) {
            int previous = Integer.parseInt(req.getParameter("previousPage"));
            setAttribute(req, "page", previous);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey("nextPage")) {
            int next = Integer.parseInt(req.getParameter("nextPage"));
            setAttribute(req, "page", next);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey("sort")) {
            int sort = Integer.parseInt(req.getParameter("sort"));
            setAttribute(req, "sort", sort);
            setAttribute(req, "page", 1);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey("language")) {
            interpreter(req);
            doGet(req, resp);
        } else {
            HttpSession session = req.getSession();
            final int id = Integer.parseInt(req.getParameter("id"));
            final int amount = Integer.parseInt(req.getParameter("amount"));

            Product product = findProductByIdWithNewAmount(req, resp, id, amount);
            Map<Integer, Product> selectedProduct = getSelectedProduct(session);
            selectedProduct.put(product.getId(), product);
            session.setAttribute("selectedProduct", selectedProduct);
        }
    }

    private void setAttribute(HttpServletRequest req, String name, int value) {
        HttpSession session = req.getSession();
        session.setAttribute(name, value);
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

    private Map<Integer, Product> getSelectedProduct(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Integer, Product> map = (Map<Integer, Product>) session.getAttribute("selectedProduct");
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    private List<Product> getProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        try {
            products = ProductRepository.getInstance().findAllProduct();
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }

        HttpSession session = req.getSession();
        session.getAttribute("sort");
        return products;
    }
}
