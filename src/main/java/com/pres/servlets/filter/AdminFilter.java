package com.pres.servlets.filter;

import com.pres.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");
        String role = user.getRole().value();

        if (!role.equals(User.Role.ADMIN.value())) {
            req.getRequestDispatcher("/view/authorization.jsp").forward(req, resp);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
