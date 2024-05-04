package com.example.ltwgk.Controller;

import com.example.ltwgk.DAO.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    UserDAO userDAO = new UserDAOImp();
    LogDAO logDAO = new LogDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String url = "/userManager.jsp";
        String error = "";
        Log log = new Log();
        log.setIp(request.getRemoteAddr());
        log.setAddress("user");
        log.setBeforeValue("EMPTY");
        log.setAfterValue("Login successfully");
        log.setLogLevel(Log_Level.INFO);
        if (!userDAO.login(email, password)) {
            url = "login.jsp";
            error = "Sai email hoac Mat khau";
            request.setAttribute("error", error);
            log.setLogLevel(Log_Level.WARNING);
            log.setAfterValue("Login failed");
        }

        logDAO.add(log);
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}