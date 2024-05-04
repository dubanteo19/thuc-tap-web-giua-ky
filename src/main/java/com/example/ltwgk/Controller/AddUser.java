package com.example.ltwgk.Controller;

import com.example.ltwgk.DAO.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddUser", value = "/AddUser")
public class AddUser extends HttpServlet {
    UserDAO userDAO = new UserDAOImp();
    LogDAO logDAO = new LogDAOImp();
    String fullName;
    String email;
    String password;
    User user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        fullName = request.getParameter("fullName");
        email = request.getParameter("email");
        password = request.getParameter("password");
        user = new User();
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        switch (action) {
            case "add" -> add(request, response);
            case "edit" -> edit(request, response);
            case "get" -> get(request, response);
        }
    }

    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        var user = userDAO.findById(id);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/editUser.jsp").forward(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        user.setId(id);
        String url = "/editUser.jsp";
        String status = "Cập nhập người dùng thành công";
        var oldUser = userDAO.findById(id).toString();
        if (!userDAO.update(user)) {
            status = "Cập nhập dùng thất bại";
        }
        var log = new Log();
        log.setIp(request.getRemoteAddr());
        log.setAddress("user");
        log.setLogLevel(Log_Level.ALERT);
        log.setBeforeValue(oldUser);
        log.setAfterValue(user.toString());
        logDAO.add(log);
        request.setAttribute("status", status);
        request.setAttribute("user", user);
        request.getRequestDispatcher(url).forward(request, response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/addUser.jsp";
        String status = "Thêm người dùng thành công";
        boolean isExist = userDAO.findByEmail(email).getId() != 0;
        if (isExist) {
            status = "Người dùng đã tồn tại";
        } else {
            status = userDAO.add(user) == 1 ? status
                    : "Thêm người dùng thất bại";
        }
        var log = new Log();
        log.setIp(request.getRemoteAddr());
        log.setAddress("user");
        log.setLogLevel(Log_Level.INFO);
        log.setBeforeValue("EMPTY");
        log.setAfterValue(user.toString());
        logDAO.add(log);
        request.setAttribute("status", status);
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}