package com.example.ltwgk.Controller;

import com.example.ltwgk.DAO.User;
import com.example.ltwgk.DAO.UserDAO;
import com.example.ltwgk.DAO.UserDAOImp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Comparator;

@WebServlet(name = "UserManager", value = "/UserManager")
public class UserManager extends HttpServlet {
    UserDAO userDAO = new UserDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String search = request.getParameter("search[value]").toLowerCase();
        String orderIndex = request.getParameter("order[0][column]") != null
                ? request.getParameter("order[0][column]")
                : "0";
        String direction = request.getParameter("order[0][dir]") != null
                ? request.getParameter("order[0][dir]")
                : "asc";
        var users = userDAO.findAll();
        var filteredUsers = users
                .stream()
                .filter(user -> user
                        .getFullName()
                        .toLowerCase()
                        .contains(search)
                        || user.getEmail()
                        .toLowerCase()
                        .contains(search))
                .skip(Long.parseLong(start))
                .limit(Long.parseLong(length))
                .sorted(getComparator(orderIndex, direction))
                .toList();
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("draw", Integer.parseInt(draw));
        jsonObject.addProperty("recordsTotal", users.size());
        jsonObject.addProperty("recordsFiltered", users.size());
        jsonObject.add("data", gson.toJsonTree(filteredUsers));
        response.getWriter().write(gson.toJson(jsonObject));
    }

    private Comparator<? super User> getComparator(String orderIndex, String direction) {
        return switch (orderIndex) {
            case "1" -> direction.equals("asc")
                    ? Comparator.comparing(User::getFullName)
                    : Comparator.comparing(User::getFullName).reversed();
            case "2" -> direction.equals("asc")
                    ? Comparator.comparing(User::getEmail)
                    : Comparator.comparing(User::getEmail).reversed();
            case "3" -> direction.equals("asc")
                    ? Comparator.comparing(User::getPassword)
                    : Comparator.comparing(User::getPassword).reversed();
            default -> direction.equals("asc")
                    ? Comparator.comparing(User::getId)
                    : Comparator.comparing(User::getId).reversed();
        };
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}