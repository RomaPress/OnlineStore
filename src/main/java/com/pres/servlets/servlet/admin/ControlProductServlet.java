package com.pres.servlets.servlet.admin;

import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.database.repository.impl.ProductRepository;
import com.pres.exception.DBException;
import com.pres.model.Product;
import com.pres.model.Type;
import com.pres.servlets.ErrorMessageHandler;
import com.pres.servlets.Internationalize;
import com.pres.util.sort.ProductSort;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig
public class ControlProductServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(ControlProductServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = ProductSort.sort(getProduct(req, resp), ProductSort.SORT_BY_ID) ;
        req.setAttribute(ServletContent.PRODUCTS, products);

        List<String> types = getType(products);
        req.setAttribute(ServletContent.TYPES, types);
        req.getRequestDispatcher(Path.PATH_TO_CONTROL_PRODUCT_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(ServletContent.UTF_8);
        if (req.getParameterMap().containsKey(ServletContent.CREATE_PRODUCT)) {
            Product product = extractProduct(req);
            Part file = req.getPart(ServletContent.FILE);
            createProduct(req, resp, product,file);
        }
        doGet(req, resp);
    }



    private void createProduct(HttpServletRequest req, HttpServletResponse resp, Product product, Part part) throws ServletException, IOException {
        try {
           ProductRepository.getInstance().createProduct(product, part);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }

    private List<Product> getProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        try {
            products = ProductRepository.getInstance().findAllProduct();
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return products;
    }

    private Product extractProduct(HttpServletRequest req) {
        return new Product.Builder()
                .setName(req.getParameter(ServletContent.NAME))
                .setPrice(Double.parseDouble(req.getParameter(ServletContent.PRICE)))
                .setAmount(Integer.parseInt(req.getParameter(ServletContent.AMOUNT)))
                .setDescription(req.getParameter(ServletContent.DESCRIPTION))
                .setType(new Type.Builder()
                        .setName(req.getParameter(ServletContent.TYPE))
                        .build())
                .build();
    }

    private List<String> getType(List<Product> products) {
        return products.stream()
                .map(Product::getType)
                .map(Type::getName)
                .distinct()
                .collect(Collectors.toList());
    }
}
