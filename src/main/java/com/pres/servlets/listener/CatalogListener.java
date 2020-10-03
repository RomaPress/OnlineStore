//package com.pres.servlets.listener;
//
//import com.pres.database.repository.impl.ProductRepository;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.util.concurrent.atomic.AtomicReference;
//
//@WebListener
//public class CatalogListener implements ServletContextListener {
//
//    private AtomicReference<ProductRepository> productDAO;
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        productDAO = new AtomicReference<>(ProductRepository.getInstance());
//
//        final ServletContext servletContext = servletContextEvent.getServletContext();
//
//        servletContext.setAttribute("products", productDAO.get().findAllProduct());
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//
//    }
//}
