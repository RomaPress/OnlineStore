package com.pres.servlets;

import com.pres.servlets.servlet.admin.ChangeOrderServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RunWith(PowerMockRunner.class)
public class ErrorMessageHandlerTest {
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher testRequestDispatcher = Mockito.mock(RequestDispatcher.class);

    @Test
    public void testHandling() throws ServletException, IOException {
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);

        ErrorMessageHandler changeOrderServlet = new ChangeOrderServlet();
        changeOrderServlet.handling(testRequest, testResponse, "MESSAGE");
    }
}