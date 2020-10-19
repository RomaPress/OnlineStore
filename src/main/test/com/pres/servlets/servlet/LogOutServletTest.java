package com.pres.servlets.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
public class LogOutServletTest  {
    @Test
    public void testPost() throws ServletException, IOException {
        LogOutServlet logOutServlet = new LogOutServlet();

        HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession testSession = Mockito.mock(HttpSession.class);
        RequestDispatcher testRequestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);
        logOutServlet.doGet(testRequest, testResponse);
    }
}