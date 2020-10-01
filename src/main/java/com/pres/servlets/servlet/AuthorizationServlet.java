package com.pres.servlets.servlet;

import com.pres.database.repository.impl.UserRepository;
import com.pres.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/view/authorization.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");
        final boolean isExist = UserRepository.getInstance().isUserAuthorization(login, password);

        if (req.getParameterMap().containsKey("loggingIn") && isExist) {
            User user = UserRepository.getInstance().getUserByLogin(login);
            moveToPage(req,resp, user);
        } else {
            doGet(req, resp);
        }
    }

    private void moveToPage(HttpServletRequest req,HttpServletResponse resp, User user) throws IOException {
        HttpSession session = req.getSession();
        session.setAttribute("currentUser", user);
        User user2 = (User) session.getAttribute("currentUser");
        if (user.getRole().equals(User.Role.ADMIN)) {
            resp.sendRedirect(req.getContextPath() + "/order");
        }else if (user.getRole().equals(User.Role.USER)) {
            resp.sendRedirect(req.getContextPath() + "/catalog");
        }
    }
}