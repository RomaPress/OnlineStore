package com.pres.servlets.filter;

import com.pres.constants.ServletContent;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding(ServletContent.UTF_8);
        resp.setCharacterEncoding(ServletContent.UTF_8);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
