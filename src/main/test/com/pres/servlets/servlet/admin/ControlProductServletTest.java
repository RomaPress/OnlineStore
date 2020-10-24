package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.UserRepository;
import junit.framework.TestCase;
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
import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
public class ControlProductServletTest  {
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher testRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    ControlProductServlet controlProductServlet = new ControlProductServlet();

    @Test
    public void testPost() throws ServletException, IOException {
        Map<String, String[]> map = new HashMap<>();
        map.put("createProduct", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        String numb = "2";
        Mockito.when(testRequest.getParameter("price")).thenReturn(numb);
        Mockito.when(testRequest.getParameter("amount")).thenReturn(numb);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);
        controlProductServlet.doPost(testRequest, testResponse);
    }
}