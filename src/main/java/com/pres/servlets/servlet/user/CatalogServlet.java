package com.pres.servlets.servlet.user;

import com.pres.constants.ErrorMessage;
import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.database.repository.impl.ProductRepository;
import com.pres.exception.DBException;
import com.pres.model.Product;
import com.pres.servlets.ErrorMessageHandler;
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

/**
 * This servlet is responsible for catalog processing. You can perform next
 * actions: see products, sort products, add products in cart, lipping product pages
 * using pagination and change page language.
 *
 * @see HttpServlet
 */
public class CatalogServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(CatalogServlet.class);
    private static final int COUNT_PRODUCT_IN_PAGE = 12;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int page = getPage(session);
        int sortAlg = getSortAlgorithm(session);
        List<Product> products = ProductSort.sort(getAllProducts(req, resp), sortAlg);
        List<Product> cutProducts = Process.cutList(products, COUNT_PRODUCT_IN_PAGE, page);

        req.setAttribute(ServletContent.PRODUCTS, cutProducts);
        req.setAttribute(ServletContent.SIZE, products.size());
        req.setAttribute(ServletContent.COUNT_PRODUCT, COUNT_PRODUCT_IN_PAGE);
        req.getRequestDispatcher(Path.PATH_TO_CATALOG_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey(ServletContent.PREVIOUS_PAGE)) {
            int previous = Integer.parseInt(req.getParameter(ServletContent.PREVIOUS_PAGE));
            setAttribute(req, ServletContent.PAGE, previous);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey(ServletContent.NEXT_PAGE)) {
            int next = Integer.parseInt(req.getParameter(ServletContent.NEXT_PAGE));
            setAttribute(req, ServletContent.PAGE, next);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey(ServletContent.SORT)) {
            int sort = Integer.parseInt(req.getParameter(ServletContent.SORT));
            setAttribute(req, ServletContent.SORT, sort);
            setAttribute(req, ServletContent.PAGE, 1);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey(ServletContent.LANGUAGE)) {
            interpret(req);
            doGet(req, resp);
        } else {
            HttpSession session = req.getSession();
            final int id = Integer.parseInt(req.getParameter(ServletContent.ID));
            final int amount = Integer.parseInt(req.getParameter(ServletContent.AMOUNT));

            Product product = findProductByIdWithNewAmount(req, resp, id, amount);
            Map<Integer, Product> selectedProduct = getSelectedProduct(session);
            selectedProduct.put(product.getId(), product);
            session.setAttribute(ServletContent.SELECTED_PRODUCT, selectedProduct);
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
            LOG.error(ErrorMessage.ERR_ADD_PRODUCT_IN_ORDER, e);
            handling(req, resp, ErrorMessage.ERR_ADD_PRODUCT_IN_ORDER);
        }
        return product;
    }

    private Map<Integer, Product> getSelectedProduct(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Integer, Product> map = (Map<Integer, Product>) session.getAttribute(ServletContent.SELECTED_PRODUCT);
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    private List<Product> getAllProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        try {
            products = ProductRepository.getInstance().findAllProduct();
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_CATALOG, e);
            handling(req, resp, ErrorMessage.ERR_CATALOG);
        }
        HttpSession session = req.getSession();
        session.getAttribute(ServletContent.SORT);
        return products;
    }

    private int getSortAlgorithm(HttpSession session) {
        int sortAlg = ProductSort.SORT_BY_ID;
        if (session.getAttribute(ServletContent.SORT) != null) {
            sortAlg = (int) session.getAttribute(ServletContent.SORT);
        }
        return sortAlg;
    }

    private int getPage(HttpSession session) {
        int page = 1;
        if (session.getAttribute(ServletContent.PAGE) != null) {
            page = (int) session.getAttribute(ServletContent.PAGE);
        }
        return page;
    }
}
