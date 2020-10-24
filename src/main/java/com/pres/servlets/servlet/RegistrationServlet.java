package com.pres.servlets.servlet;

import com.pres.constants.ErrorMessage;
import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.database.repository.impl.UserRepository;
import com.pres.exception.DBException;
import com.pres.model.User;
import com.pres.servlets.ErrorMessageHandler;
import com.pres.servlets.Internationalize;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet is responsible for registration processing.
 * You can perform registration.
 *
 * @see HttpServlet
 */
public class RegistrationServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(ServletContent.UTF_8);
        String login = req.getParameter(ServletContent.LOGIN);
        boolean isExist = isLoginExists(req, resp, login);

        if (isExist) {
            resp.getWriter().write(String.valueOf(isExist));
        } else if (req.getParameterMap().containsKey(ServletContent.LANGUAGE)) {
            interpret(req);
            resp.sendRedirect(req.getContextPath() + Path.URL_TO_AUTHENTICATION_PAGE);
        } else {
            User user = extractUser(req);
            if (createUser(req, resp, user)){
                resp.sendRedirect(req.getContextPath() + Path.URL_TO_AUTHENTICATION_PAGE);
            }
        }
    }

    private boolean isLoginExists(HttpServletRequest req, HttpServletResponse resp, String login) throws ServletException, IOException {
        boolean isExist = false;
        try {
            if (UserRepository.getInstance().isLoginExist(login)) {
                isExist = true;
            }
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return isExist;
    }

    private User extractUser(HttpServletRequest req) {
        return new User.Builder()
                .setFirstName(req.getParameter(ServletContent.FIRST_NAME))
                .setLastName(req.getParameter(ServletContent.LAST_NAME))
                .setPhoneNumber(req.getParameter(ServletContent.PHONE_NUMBER))
                .setLogin(req.getParameter(ServletContent.LOGIN))
                .setPassword(req.getParameter(ServletContent.PASSWORD))
                .setPostOffice(Integer.parseInt(req.getParameter(ServletContent.POST_OFFICE)))
                .setCity(req.getParameter(ServletContent.CITY))
                .build();
    }

    private boolean createUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        try {
            UserRepository.getInstance().createUser(user);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_REGISTRATION, e);
            handling(req, resp, ErrorMessage.ERR_REGISTRATION);
            return false;
        }
        return true;
    }
}
