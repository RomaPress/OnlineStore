package com.pres.servlets.servlet.user;

import com.pres.database.repository.impl.UserRepository;
import com.pres.exception.DBException;
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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UserRepository.class)
public class ProfileServletTest {
    HttpServletRequest testRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse testResponse = Mockito.mock(HttpServletResponse.class);
    HttpSession testSession = Mockito.mock(HttpSession.class);
    RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
    PrintWriter testPrintWriter =  Mockito.mock(PrintWriter.class);
    @Test
    public void testPost() throws ServletException, IOException, DBException {
        ProfileServlet profileServlet = new ProfileServlet();
        String str = "12345";
        Mockito.when(testRequest.getParameter("password")).thenReturn(str);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        User user = new User.Builder().setPassword(str).build();
        Mockito.when(testSession.getAttribute("currentUser")).thenReturn(user);
        UserRepository mock = PowerMockito.mock(UserRepository.class);
        mockStatic(UserRepository.class);
        PowerMockito.when(UserRepository.getInstance()).thenReturn(mock);
        String postOfficeStr = "5";
        User user1 = new User.Builder()
                .setId(2)
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPhoneNumber("phoneNumber")
                .setPostOffice(Integer.parseInt(postOfficeStr))
                .setCity("city")
                .setPassword(str)
                .build();
        Mockito.when(testSession.getAttribute("currentUser")).thenReturn(user1);

        Mockito.when(mock.updateUserInfo(user1, 1)).thenReturn(true);


        Mockito.when(testRequest.getParameter("postOffice")).thenReturn(postOfficeStr);
        Mockito.when(testResponse.getWriter()).thenReturn(testPrintWriter);

        profileServlet.doPost(testRequest, testResponse);
    }

    @Test
    public void testPostLanguage() throws ServletException, IOException {
        ProfileServlet profileServlet = new ProfileServlet();
        Map<String, String[]> map = new HashMap<>();
        map.put("language", new String[]{});
        String languageStr = "en";
        Mockito.when(testRequest.getParameterMap()).thenReturn(map);
        Mockito.when(testRequest.getParameter("language")).thenReturn(languageStr);
        Mockito.when(testRequest.getSession()).thenReturn(testSession);
        Mockito.doNothing().when(testSession).setAttribute("language", languageStr);
        Mockito.when(testRequest.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        profileServlet.doPost(testRequest, testResponse);
    }
}