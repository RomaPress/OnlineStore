package com.pres.servlets.servlet;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * This servlet is responsible for authentication processing. You can perform next
 * actions: log in how admin, log in how user, log in without authentication
 * and change page language.
 *
 * @see HttpServlet
 */
public class AuthenticationServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(AuthenticationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(Path.PATH_TO_AUTHENTICATION_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String login = req.getParameter(ServletContent.LOGIN);
        final String password = req.getParameter(ServletContent.PASSWORD);
        final boolean isExist = isUserAuthorized(req, resp, login, password);

        if (req.getParameterMap().containsKey(ServletContent.LOG_IN) && isExist) {
            User user = getUserByLogin(req, resp, login);
            moveToPage(req, resp, user);
        }else if (req.getParameterMap().containsKey(ServletContent.LANGUAGE)) {
            interpret(req);
            resp.sendRedirect(req.getContextPath() + Path.URL_TO_AUTHENTICATION_PAGE);
        }  else {
            doGet(req, resp);
        }
    }

    private void moveToPage(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute(ServletContent.CURRENT_USER, user);

        if (user.getRole().equals(User.Role.ADMIN)) {
            resp.sendRedirect(req.getContextPath() + Path.URL_TO_ADMIN_MENU_PAGE);
        } else {
            resp.sendRedirect(req.getContextPath() + Path.URL_TO_CATALOG_PAGE);
        }
    }

    private boolean isUserAuthorized(HttpServletRequest req, HttpServletResponse resp,
                                     String login, String password) throws ServletException, IOException {
        boolean answer = false;
        try {
            answer = UserRepository.getInstance().isUserAuthorized(login, password);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return answer;
    }

    public User getUserByLogin(HttpServletRequest req, HttpServletResponse resp, String login) throws ServletException, IOException {
        User user = null;
        try {
            user = UserRepository.getInstance().getUserByLogin(login);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return user;
    }
}