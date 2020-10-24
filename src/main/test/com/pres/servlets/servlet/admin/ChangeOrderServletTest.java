package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.exception.DBException;
import com.pres.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
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

import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OrderRepository.class)
public class ChangeOrderServletTest {
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    HttpSession testSession = Mockito.mock(HttpSession.class);
    RequestDispatcher testRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    ChangeOrderServlet changeOrderServlet = new ChangeOrderServlet();

    @Test
    public void testPostUpdate() throws ServletException, IOException, DBException {
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Order order = new Order();
        int id = 1;
        order.setId(id);
        Mockito.when(testSession.getAttribute("order")).thenReturn(order);
        Map<String, String[]> map = new HashMap<>();
        map.put("update", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        OrderRepository mock = PowerMockito.mock(OrderRepository.class);
        mockStatic(OrderRepository.class);
        PowerMockito.when(OrderRepository.getInstance()).thenReturn(mock);
        Mockito.when(mock.findOrderById(1)).thenReturn(order);
        Mockito.when(testRequest.getParameter("status")).thenReturn("status");
        Mockito.when(testRequest.getParameter("invoiceNumber")).thenReturn("invoiceNumber");
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);
        changeOrderServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPostDelete() throws ServletException, IOException, DBException {
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Order order = new Order();
        int id = 1;
        order.setId(id);
        Mockito.when(testSession.getAttribute("order")).thenReturn(order);
        Map<String, String[]> map = new HashMap<>();
        map.put("delete", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("product_id")).thenReturn("3");
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);
        changeOrderServlet.doPost(testRequest, testResponse);
    }
}