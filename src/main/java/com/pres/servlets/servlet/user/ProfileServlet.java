package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.database.repository.impl.UserRepository;
import com.pres.exeption.DBException;
import com.pres.model.Order;
import com.pres.model.User;
import com.pres.servlets.ErrorCatchable;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfileServlet extends HttpServlet implements ErrorCatchable {
    private static final Logger LOG = Logger.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req);
        List<Order> orders = getOrderByUser(req, resp, user);
        req.setAttribute("userOrder", orders);
        req.getRequestDispatcher("jsp/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String oldPassword = req.getParameter("password");

        boolean isCorrect = checkPassword(oldPassword, req);
        if (isCorrect) {
            User user = extractUser(req);
            saveUser(req, resp, user);
            resp.sendRedirect(req.getContextPath() + "/profile");
        }
        resp.getWriter().write(String.valueOf(isCorrect));

    }

    private boolean checkPassword(String password, HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) (session.getAttribute("currentUser"));
        return password.equals(user.getPassword());
    }

    private void saveUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User oldUserData = (User) (session.getAttribute("currentUser"));

        if (updateUserInfo(req, resp, user, oldUserData.getId())) {
            User newUserData = getUserByLogin(req, resp, oldUserData.getLogin());
            session.setAttribute("currentUser", newUserData);
        }
    }

    private User getUserByLogin(HttpServletRequest req, HttpServletResponse resp, String login) throws ServletException, IOException {
        User user = null;
        try {
            user = UserRepository.getInstance().getUserByLogin(login);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return user;
    }

    private boolean updateUserInfo(HttpServletRequest req, HttpServletResponse resp, User user, int id) throws ServletException, IOException {
        boolean answer = false;
        try {
            answer = UserRepository.getInstance().updateUserInfo(user, id);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return answer;
    }

    private User extractUser(HttpServletRequest req) {

        return new User.Builder()
                .setFirstName(req.getParameter("firstName"))
                .setLastName(req.getParameter("lastName"))
                .setPhoneNumber(req.getParameter("phoneNumber"))
                .setPostOffice(Integer.parseInt(req.getParameter("postOffice")))
                .setCity(req.getParameter("city"))
                .build();
    }

    private User getUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute("currentUser");
    }

    private List<Order> getOrderByUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        List<Order> orders = null;
        try {
            orders = OrderRepository.getInstance().findOrderByUser(user);
        } catch (DBException e) {
            LOG.error(e.getMessage(), e);
            handling(req, resp, e.getMessage());
        }
        return orders;
    }
}
