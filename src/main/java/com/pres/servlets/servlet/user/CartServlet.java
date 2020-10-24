package com.pres.servlets.servlet.user;

import com.pres.constants.ErrorMessage;
import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.database.repository.impl.OrderRepository;
import com.pres.exception.DBException;
import com.pres.model.Product;
import com.pres.model.User;
import com.pres.servlets.ErrorMessageHandler;
import com.pres.servlets.Internationalize;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


/**
 * This servlet is responsible for cart processing. You can perform next
 * actions: delete product from cart, make an order, see selected products
 * and change page language.
 *
 * @see HttpServlet
 */
public class CartServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(CartServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Integer, Product> map = getSelectedProduct(session);

        req.setAttribute(ServletContent.SELECTED_PRODUCT, map);
        req.getRequestDispatcher(Path.PATH_TO_CART_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Integer, Product> selectedProduct = getSelectedProduct(session);

        if (req.getParameterMap().containsKey(ServletContent.DELETE)) {
            final int id = Integer.parseInt(req.getParameter(ServletContent.DELETE_ID));
            selectedProduct.remove(id);
            setSelectedProduct(session, selectedProduct);
            doGet(req, resp);
        } else if (req.getParameterMap().containsKey(ServletContent.ORDER) && selectedProduct.size() != 0) {
            User user = (User) session.getAttribute(ServletContent.CURRENT_USER);
            makeOrder(req, resp, selectedProduct, user);
            resp.sendRedirect(req.getContextPath() + Path.URL_TO_CATALOG_PAGE);
        } else if (req.getParameterMap().containsKey(ServletContent.LANGUAGE)) {
            interpret(req);
            doGet(req, resp);
        }
    }

    private Map<Integer, Product> getSelectedProduct(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Integer, Product> answer = (Map<Integer, Product>) session.getAttribute(ServletContent.SELECTED_PRODUCT);
        return answer;
    }

    private void setSelectedProduct(HttpSession session, Map<Integer, Product> map) {
        session.setAttribute(ServletContent.SELECTED_PRODUCT, map);
    }

    private void makeOrder(HttpServletRequest req, HttpServletResponse resp,
                           Map<Integer, Product> selectedProduct, User user) throws ServletException, IOException {
        try {
            OrderRepository.getInstance().doOrder(selectedProduct, user);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_CREATE_ORDER, e);
            handling(req, resp, ErrorMessage.ERR_CREATE_ORDER);
        }
    }
}
