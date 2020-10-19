package com.pres.servlets.servlet;

import com.pres.database.repository.impl.UserRepository;
import com.pres.exception.DBException;
import com.pres.model.User;
import com.pres.servlets.ErrorCatchable;
import com.pres.servlets.Internationalize;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationServlet extends HttpServlet implements ErrorCatchable, Internationalize {
    private static final Logger LOG = Logger.getLogger(AuthenticationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/authentication.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final boolean isExist = isUserAuthorized(req, resp, login, password);

        if (req.getParameterMap().containsKey("logIn") && isExist) {
            User user = getUserByLogin(req, resp, login);
            moveToPage(req, resp, user);
        } else {
            doGet(req, resp);
        }
    }

    private void moveToPage(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute("currentUser", user);

        if (user.getRole().equals(User.Role.ADMIN)) {
            resp.sendRedirect(req.getContextPath() + "/order");
        } else {
            resp.sendRedirect(req.getContextPath() + "/catalog");
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