package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.database.repository.impl.UserRepository;
import com.pres.model.Order;
import com.pres.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");
        List<Order> orders = OrderRepository.getInstance().findOrderByUser(user);
        req.setAttribute("userOrder", orders);
        req.getRequestDispatcher("view/user/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String oldPassword = req.getParameter("password");

        if (checkPassword(oldPassword, req)) {
            User user = new User.Builder()
                    .setFirstName(req.getParameter("firstName"))
                    .setLastName(req.getParameter("lastName"))
                    .setPhoneNumber(req.getParameter("phoneNumber"))
                    .setPostOffice(Integer.parseInt(req.getParameter("postOffice")))
                    .setCity(req.getParameter("city"))
                    .build();
            saveUser(user, req);
        }
        resp.sendRedirect(req.getContextPath() + "/profile");
    }

    private boolean checkPassword(String password, HttpServletRequest req) {
        HttpSession session = req.getSession();
        User user = (User) (session.getAttribute("currentUser"));
        return password.equals(user.getPassword());
    }

    private void saveUser(User user, HttpServletRequest req){
        HttpSession session = req.getSession();
        User oldUserData = (User) (session.getAttribute("currentUser"));

        if (UserRepository.getInstance().updateUserInfo(user, oldUserData.getId())){
            User newUserData =  UserRepository.getInstance().getUserByLogin(oldUserData.getLogin());
            session.setAttribute("currentUser", newUserData);
        }
    }
}
