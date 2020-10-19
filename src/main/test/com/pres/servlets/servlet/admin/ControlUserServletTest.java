package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserRepository.class)
public class ControlUserServletTest  {
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    HttpSession testSession = Mockito.mock(HttpSession.class);
    RequestDispatcher testRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    ControlUserServlet registrationServlet = new ControlUserServlet();

    @Test
    public void testPostActionBlock() throws ServletException, IOException {
        String id = "1";
        String action = "block";
        Mockito.when(testRequest.getParameter("action")).thenReturn(action);
        Mockito.when(testRequest.getParameter("user_id")).thenReturn(id);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);

        registrationServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPostActionUnlock() throws ServletException, IOException {
        String id = "1";
        String action = "unlock";
        Mockito.when(testRequest.getParameter("action")).thenReturn(action);
        Mockito.when(testRequest.getParameter("user_id")).thenReturn(id);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);

        registrationServlet.doPost(testRequest, testResponse);
    }
}