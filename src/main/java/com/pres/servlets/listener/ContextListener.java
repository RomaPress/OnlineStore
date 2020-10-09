package com.pres.servlets.listener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);


    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();
        initLog4J(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void initLog4J(ServletContext servletContext) {
        PropertyConfigurator.configure(servletContext.getRealPath("WEB-INF/log4j.properties"));
        LOG.debug("Log4j has been initialized");
    }
}
