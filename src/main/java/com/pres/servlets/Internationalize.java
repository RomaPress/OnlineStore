package com.pres.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface Internationalize {

    default void interpreter(HttpServletRequest req){
        String locale = req.getParameter("language");
        HttpSession session = req.getSession();
        session.setAttribute("language", locale);
    }
}
