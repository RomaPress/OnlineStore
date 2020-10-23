package com.pres.servlets.servlet.admin;

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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This servlet is responsible for user processing. You can perform next
 * actions: see users, block users and unlock users.
 *
 * @see HttpServlet
 */
public class ControlUserServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(ControlUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = getUser(req, resp);
        req.setAttribute(ServletContent.USERS, users);
        req.getRequestDispatcher(Path.PATH_TO_CONTROL_USER_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter(ServletContent.ACTION);
        int userId = Integer.parseInt(req.getParameter(ServletContent.USER_ID));

        if (action.equals(ServletContent.BLOCK)) {
            blockUser(req, resp, userId);
        } else if (action.equals(ServletContent.UNLOCK)) {
            unlockUser(req, resp, userId);
        }
        doGet(req, resp);
    }

    private List<User> getUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = new ArrayList<>();
        try {
            users = UserRepository.getInstance().findAllUser();
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return users.stream()
                .filter(user -> User.Role.ADMIN != user.getRole())
                .collect(Collectors.toList());
    }

    private void blockUser(HttpServletRequest req, HttpServletResponse resp, int userId) throws ServletException, IOException {
        try {
            UserRepository.getInstance().updateUserRole(userId, User.Role.BLOCK.value());
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }

    private void unlockUser(HttpServletRequest req, HttpServletResponse resp, int userId) throws ServletException, IOException {
        try {
            UserRepository.getInstance().updateUserRole(userId, User.Role.USER.value());
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
    }
}
