package com.example.ltwgk.Controller;

import com.example.ltwgk.DAO.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Delete", value = "/Delete")
public class Delete extends HttpServlet {
    UserDAO userDAO = new UserDAOImp();
    LogDAO logDAO = new LogDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        var oldUser = userDAO.findById(id).toString();
        var deleteRow = userDAO.delete(id);
        var log = new Log();
        log.setIp(request.getRemoteAddr());
        log.setAddress("user");
        log.setLogLevel(Log_Level.DANGER);
        log.setBeforeValue(oldUser);
        log.setAfterValue("User deleted");
        logDAO.add(log);
        response.getWriter().write(String.valueOf(deleteRow));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}