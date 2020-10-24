package com.pres.servlets;

import com.pres.servlets.servlet.admin.ChangeOrderServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RunWith(PowerMockRunner.class)
public class InternationalizeTest {
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession testSession = Mockito.mock(HttpSession.class);
    @Test
    public void testHandling(){
        Mockito.when(testRequest.getParameter("language")).thenReturn("ru");
        Mockito.when(testRequest.getSession()).thenReturn(testSession);

        Internationalize changeOrderServlet = new ChangeOrderServlet();
        changeOrderServlet.interpret(testRequest);
    }
}