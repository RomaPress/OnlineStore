package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.ProductRepository;
import com.pres.exception.DBException;
import com.pres.model.Product;
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
@PrepareForTest(ProductRepository.class)
public class CatalogServletTest {

    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    HttpSession testSession = Mockito.mock(HttpSession.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

    @Test
    public void testPostPreviousPage() throws ServletException, IOException {
        CatalogServlet catalogServlet = new CatalogServlet();

        Map<String, String[]> map = new HashMap<>();
        map.put("previousPage", new String[]{});
        String pageStr = "5";
        int page = Integer.parseInt(pageStr);
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("previousPage")).thenReturn(pageStr);
        Mockito.doNothing().when(testSession).setAttribute("page", page);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        catalogServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPostNextPage() throws ServletException, IOException {
        CatalogServlet catalogServlet = new CatalogServlet();

        Map<String, String[]> map = new HashMap<>();
        map.put("nextPage", new String[]{});
        String pageStr = "5";
        int page = Integer.parseInt(pageStr);
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("nextPage")).thenReturn(pageStr);
        Mockito.doNothing().when(testSession).setAttribute("page", page);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        catalogServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPostSort() throws ServletException, IOException {
        CatalogServlet catalogServlet = new CatalogServlet();

        Map<String, String[]> map = new HashMap<>();
        map.put("sort", new String[]{});
        String sortStr = "5";
        int sort = Integer.parseInt(sortStr);
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("sort")).thenReturn(sortStr);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.doNothing().when(testSession).setAttribute("sort", sort);
        Mockito.doNothing().when(testSession).setAttribute("page", 1);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        catalogServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPostLanguage() throws ServletException, IOException {
        CatalogServlet catalogServlet = new CatalogServlet();

        Map<String, String[]> map = new HashMap<>();
        map.put("language", new String[]{});

        String languageStr = "en";
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("language")).thenReturn(languageStr);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.doNothing().when(testSession).setAttribute("language", languageStr);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        catalogServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPost() throws ServletException, IOException, DBException {
        CatalogServlet catalogServlet = new CatalogServlet();
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        String idStr = "1";
        String amountStr = "2";
        int id = Integer.parseInt(idStr);
        int amount = Integer.parseInt(amountStr);
        Mockito.when(testRequest.getParameter("id")).thenReturn(idStr);
        Mockito.when(testRequest.getParameter("amount")).thenReturn(amountStr);

        ProductRepository mock = PowerMockito.mock(ProductRepository.class);
        mockStatic(ProductRepository.class);
        PowerMockito.when(ProductRepository.getInstance()).thenReturn(mock);
        Product product = new Product.Builder()
                .setId(id)
                .setAmount(amount)
                .build();

        Mockito.when(mock.findProductByIdWithNewAmount(id, amount)).thenReturn(product);

        catalogServlet.doPost(testRequest, testResponse);
    }
}