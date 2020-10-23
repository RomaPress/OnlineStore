package com.pres.servlets;

import com.pres.constants.ServletContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Implementing this interface allows a class to set a preferred
 * language from request.
 *
 * @see HttpServletRequest
 *
 * @author Pres Roman
 */
public interface Internationalize {

    default void interpret(HttpServletRequest req){
        String locale = req.getParameter(ServletContent.LANGUAGE);
        HttpSession session = req.getSession();
        session.setAttribute(ServletContent.LANGUAGE, locale);
    }
}
