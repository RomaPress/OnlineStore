package com.pres.servlets.servlet.user;

import com.pres.constants.ErrorMessage;
import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.database.repository.impl.OrderRepository;
import com.pres.database.repository.impl.UserRepository;
import com.pres.exception.DBException;
import com.pres.model.Order;
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
import java.util.List;

/**
 * This servlet is responsible for user profile processing. You can perform next
 * actions: see user order, change user data and change page language.
 *
 * @see HttpServlet
 */
public class ProfileServlet extends HttpServlet implements ErrorMessageHandler, Internationalize {
    private static final Logger LOG = Logger.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req);
        List<Order> orders = getOrderByUser(req, resp, user);
        req.setAttribute(ServletContent.USER_ORDER, orders);
        req.getRequestDispatcher(Path.PATH_TO_USER_PROFILE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterMap().containsKey(ServletContent.LANGUAGE)) {
            interpret(req);
            doGet(req, resp);
        } else {
            req.setCharacterEncoding(ServletContent.UTF_8);
            String oldPassword = req.getParameter(ServletContent.PASSWORD);

            boolean isCorrect = checkPassword(oldPassword, req);
            if (isCorrect) {
                User user = extractUser(req);
                saveUser(req, resp, user);
                resp.sendRedirect(req.getContextPath() + Path.URL_TO_USER_PROFILE_PAGE);
            }
            resp.getWriter().write(String.valueOf(isCorrect));
        }
    }

    private boolean checkPassword(String password, HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) (session.getAttribute(ServletContent.CURRENT_USER));
        return password.equals(user.getPassword());
    }

    private void saveUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User oldUserData = (User) (session.getAttribute(ServletContent.CURRENT_USER));

        if (updateUserInfo(req, resp, user, oldUserData.getId())) {
            User newUserData = getUserByLogin(req, resp, oldUserData.getLogin());
            session.setAttribute(ServletContent.CURRENT_USER, newUserData);
        }
    }

    private User getUserByLogin(HttpServletRequest req, HttpServletResponse resp, String login) throws ServletException, IOException {
        User user = null;
        try {
            user = UserRepository.getInstance().getUserByLogin(login);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_CHANGE_USER_DATA, e);
            handling(req, resp, ErrorMessage.ERR_CHANGE_USER_DATA);
        }
        return user;
    }

    private boolean updateUserInfo(HttpServletRequest req, HttpServletResponse resp, User user, int id) throws ServletException, IOException {
        boolean answer = false;
        try {
            answer = UserRepository.getInstance().updateUserInfo(user, id);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_CHANGE_USER_DATA, e);
            handling(req, resp, ErrorMessage.ERR_CHANGE_USER_DATA);
        }
        return answer;
    }

    private User extractUser(HttpServletRequest req) {
        return new User.Builder()
                .setFirstName(req.getParameter(ServletContent.FIRST_NAME))
                .setLastName(req.getParameter(ServletContent.LAST_NAME))
                .setPhoneNumber(req.getParameter(ServletContent.PHONE_NUMBER))
                .setPostOffice(Integer.parseInt(req.getParameter(ServletContent.POST_OFFICE)))
                .setCity(req.getParameter(ServletContent.CITY))
                .build();
    }

    private User getUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute(ServletContent.CURRENT_USER);
    }

    private List<Order> getOrderByUser(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        List<Order> orders = null;
        try {
            orders = OrderRepository.getInstance().findOrderByUser(user);
        } catch (DBException e) {
            LOG.error(ErrorMessage.ERR_USER_PROFILE, e);
            handling(req, resp, ErrorMessage.ERR_USER_PROFILE);
        }
        return orders;
    }
}
