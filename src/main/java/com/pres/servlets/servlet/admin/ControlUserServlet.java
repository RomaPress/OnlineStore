package com.pres.servlets.servlet.admin;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ControlUserServlet extends HttpServlet implements ErrorCatchable, Internationalize {
    private static final Logger LOG = Logger.getLogger(ControlUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = getUser(req, resp);
        req.setAttribute("users", users);
        req.getRequestDispatcher("/jsp/admin/control_user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        int userId = Integer.parseInt(req.getParameter("user_id"));

        if (action.equals("block")) {
            blockUser(req, resp, userId);
        } else if (action.equals("unlock")) {
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
