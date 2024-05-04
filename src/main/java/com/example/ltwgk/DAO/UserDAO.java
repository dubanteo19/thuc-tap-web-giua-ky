package com.example.ltwgk.DAO;

import java.util.List;

public interface UserDAO {
    List<User> findAll();

    int add(User user);

    boolean delete(int id);

    boolean update(User user);

    boolean login(String email, String password);

    User findByEmail(String email);

    User findById(int id);
}
