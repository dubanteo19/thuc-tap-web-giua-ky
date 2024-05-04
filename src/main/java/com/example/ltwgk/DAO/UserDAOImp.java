package com.example.ltwgk.DAO;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class UserDAOImp implements UserDAO {

    Jdbi jdbi;

    public UserDAOImp() {
        String url = "jdbc:mysql://localhost:3306/ltw";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        jdbi = Jdbi.create(url, "root", "");
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(User.class).list());
    }

    @Override
    public int add(User user) {
        String sql = "INSERT INTO users(fullName,email,password) VALUES(?,?,?)";
        return jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind(0, user.getFullName())
                .bind(1, user.getEmail())
                .bind(2, user.getPassword()).execute());
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        int deletedRow = 0;
        deletedRow = jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind(0, id).execute());
        return deletedRow > 0;
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE  users SET fullname = ?, email = ? , password =?" +
                "WHERE id =?";

        return jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind(0, user.getFullName())
                .bind(1, user.getEmail())
                .bind(2, user.getPassword())
                .bind(3, user.getId())
                .execute()) > 0;
    }

    @Override
    public boolean login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        return !jdbi.withHandle(handle -> handle
                .select(sql, email, password)
                .mapToBean(User.class)
                .list()).isEmpty();
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        String sql = "SELECT * FROM users WHERE email=?";
        user = jdbi.withHandle(handle -> handle
                .select(sql, email)
                .mapToBean(User.class)
                .list()
                .stream()
                .findFirst()
                .orElse(new User()));
        return user;
    }

    @Override
    public User findById(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE id=?";
        user = jdbi.withHandle(handle -> handle
                .select(sql, id)
                .mapToBean(User.class)
                .list()
                .stream()
                .findFirst()
                .orElse(new User()));
        return user;
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAOImp();
        User user = new User();
        user.setEmail("abc@gmail.com");
        user.setFullName("Du Ban Teo");
        user.setPassword("password");
        System.out.println(userDAO.login("xiy@gmail.com", "dvMfnazb2R"));
//        int insertedRow = userDAO.add(user);
//        System.out.println(insertedRow);
//        userDAO.findAll()
//                .forEach(System.out::println);
    }
}
