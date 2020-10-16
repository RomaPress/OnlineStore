package com.pres.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ErrorCatchable {

    default void handling(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
        if (!message.isEmpty()) {
            req.setAttribute("errorMessage", message);
        }
        req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
    }
}
