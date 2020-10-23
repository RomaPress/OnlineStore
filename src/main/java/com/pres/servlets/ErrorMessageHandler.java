package com.pres.servlets;

import com.pres.constants.Path;
import com.pres.constants.ServletContent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Implementing this interface allows a class to pass exception message to request
 * and show it on the error page.
 *
 * @author Pres Roman
 */
public interface ErrorMessageHandler {

    default void handling(HttpServletRequest req, HttpServletResponse resp, String message) throws ServletException, IOException {
        if (!message.isEmpty()) {
            req.setAttribute(ServletContent.ERROR_MESSAGE, message);
        }
        req.getRequestDispatcher(Path.PATH_TO_ERROR_PAGE).forward(req, resp);
    }
}
