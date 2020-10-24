package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.ProductRepository;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ProductRepository.class)
public class ChangeProductServletTest {
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    RequestDispatcher testRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    ChangeProductServlet changeProductServlet = new ChangeProductServlet();

    @Test
    public void testPostChange() throws ServletException, IOException {
        Mockito.when(testRequest.getParameter("product_id")).thenReturn("4");
        Map<String, String[]> map = new HashMap<>();
        map.put("change", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("product_id")).thenReturn("4");
        Mockito.when(testRequest.getParameter("amount")).thenReturn("4");
        Mockito.when(testRequest.getParameter("price")).thenReturn("4");
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);
        changeProductServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPostDelete() throws ServletException, IOException {
        Mockito.when(testRequest.getParameter("product_id")).thenReturn("4");
        Map<String, String[]> map = new HashMap<>();
        map.put("delete", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);
        changeProductServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testGet() throws ServletException, IOException {
        Map<String, String[]> map = new HashMap<>();
        map.put("update", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("product_id")).thenReturn("4");
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);
        changeProductServlet.doGet(testRequest, testResponse);
    }
}