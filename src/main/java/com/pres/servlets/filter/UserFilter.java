package com.pres.servlets.filter;

import com.pres.constants.Path;
import com.pres.constants.ServletContent;
import com.pres.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletContent.CURRENT_USER);
        if (user == null || !(user.getRole().value()).equals(User.Role.USER.value())) {
            session.invalidate();
            req.getRequestDispatcher(Path.PATH_TO_AUTHENTICATION_PAGE).forward(req, resp);
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
