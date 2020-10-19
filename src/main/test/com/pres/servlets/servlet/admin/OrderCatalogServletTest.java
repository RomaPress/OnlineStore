package com.pres.servlets.servlet.admin;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.database.repository.impl.UserRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OrderRepository.class)
public class OrderCatalogServletTest  {


    @Test
    public void testPost() throws DBException, ServletException, IOException {
        OrderCatalogServlet orderCatalogServlet = new OrderCatalogServlet();

        HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
        HttpSession testSession = Mockito.mock(HttpSession.class);
        OrderRepository mock = PowerMockito.mock(OrderRepository.class);
        mockStatic(OrderRepository.class);

        PowerMockito.when(OrderRepository.getInstance()).thenReturn(mock);

        String idStr ="5";
        int id = Integer.parseInt(idStr);

        Mockito.when(testRequest.getParameter("order_id")).thenReturn(idStr);

        Order order = new Order();
        order.setId(id);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.when(mock.findOrderById(id)).thenReturn(order);

        orderCatalogServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testGet() throws DBException, ServletException, IOException {
        OrderCatalogServlet orderCatalogServlet = new OrderCatalogServlet();
        HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
        OrderRepository mock = PowerMockito.mock(OrderRepository.class);
        RequestDispatcher testRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        mockStatic(OrderRepository.class);
        PowerMockito.when(OrderRepository.getInstance()).thenReturn(mock);

       List<Order> orderList = new ArrayList<>();
       orderList.add(new Order());
        Mockito.when(mock.findAllOrders()).thenReturn(orderList);

        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(testRequestDispatcher);

        orderCatalogServlet.doGet(testRequest, testResponse);

    }
}