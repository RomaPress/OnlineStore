package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.OrderRepository;
import com.pres.database.repository.impl.UserRepository;
import com.pres.exception.DBException;
import com.pres.model.Product;
import com.pres.model.User;
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
public class CartServletTest {

    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    HttpSession testSession = Mockito.mock(HttpSession.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);


    @Test
    public void testPostDoOrder() throws DBException, ServletException, IOException {
        CartServlet cartServlet = new CartServlet();
        OrderRepository mock = PowerMockito.mock(OrderRepository.class);
        mockStatic(OrderRepository.class);
        PowerMockito.when(OrderRepository.getInstance()).thenReturn(mock);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Map<Integer, Product> map = new HashMap<>();
        map.put(1, new Product.Builder()
                .setId(1)
                .setName("PRODUCT")
                .setAmount(2)
                .build());
        Mockito.when(testSession.getAttribute("selectedProduct")).thenReturn(map);
        Map<String, String[]> map2 = new HashMap<>();
        map2.put("order", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map2);
        User user = new User.Builder()
                .setId(1)
                .setFirstName("Roma")
                .setLastName("Pres")
                .setCity("Poltava")
                .setPhoneNumber("+380777")
                .setRole(User.Role.ADMIN.value())
                .setPostOffice(68)
                .build();
        Mockito.when(testSession.getAttribute("currentUser")).thenReturn(user);
        Mockito.doNothing().when(mock).doOrder(map, user);
        cartServlet.doPost(testRequest, testResponse);
    }
    @Test
    public void testPostLanguage() throws ServletException, IOException {
        CartServlet cartServlet = new CartServlet();

        Map<String, String[]> map = new HashMap<>();
        map.put("language", new String[]{});

        String languageStr = "en";
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("language")).thenReturn(languageStr);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.doNothing().when(testSession).setAttribute("language", languageStr);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        cartServlet.doPost(testRequest, testResponse);
    }
    @Test
    public void testPostDelete() throws DBException, ServletException, IOException {
        CartServlet cartServlet = new CartServlet();
        OrderRepository mock = PowerMockito.mock(OrderRepository.class);
        mockStatic(OrderRepository.class);
        PowerMockito.when(OrderRepository.getInstance()).thenReturn(mock);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.when(testSession.getAttribute("selectedProduct")).thenReturn(new HashMap<>());
        Map<String, String[]> map = new HashMap<>();
        map.put("delete", new String[]{});
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("deleteId")).thenReturn("3");
        User user = new User.Builder()
                .setId(1)
                .setFirstName("Roma")
                .setLastName("Pres")
                .setCity("Poltava")
                .setPhoneNumber("+380777")
                .setRole(User.Role.ADMIN.value())
                .setPostOffice(68)
                .build();
        Mockito.when(testSession.getAttribute("currentUser")).thenReturn(user);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        cartServlet.doPost(testRequest, testResponse);
    }

}