package com.example.ltwgk.Controller;

import com.example.ltwgk.DAO.LogDAO;
import com.example.ltwgk.DAO.LogDAOImp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogManager", value = "/LogManager")
public class LogManager extends HttpServlet {
    LogDAO logDAO = new LogDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var logs = logDAO.findAll();
        request.setAttribute("logs", logs);
        request.getRequestDispatcher("/logManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}