package com.pres.servlets.listener;

import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.util.file.Photo;
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
        getServerPath(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private void initLog4J(ServletContext servletContext) {
        PropertyConfigurator.configure(servletContext.getRealPath(Path.PATH_TO_LOG4J));
        LOG.debug("Log4j has been initialized");
    }

    private void getServerPath(ServletContext servletContext){
        String path = String.join("", servletContext
                .getRealPath("")
                .split(ServletContent.PROJECT_NAME + ".+", 0));
        Photo.init(path);
        LOG.debug("Absolute path has been initialized");
    }
}
