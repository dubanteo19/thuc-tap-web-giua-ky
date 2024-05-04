package com.example.ltwgk.DAO;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class LogDAOImp implements LogDAO {
    Jdbi jdbi;

    public LogDAOImp() {
        String url = "jdbc:mysql://localhost:3306/ltw";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        jdbi = Jdbi.create(url, "root", "");
    }

    @Override
    public int add(Log log) {
        String sql = """
                INSERT INTO logs(logLevel,address,ip,beforeValue,afterValue)
                VALUES (?,?,?,?,?)
                """;
        return jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind(0, log.getLogLevel().toString())
                .bind(1, log.getAddress())
                .bind(2, log.getIp())
                .bind(3, log.getBeforeValue())
                .bind(4, log.getAfterValue())
                .execute());
    }

    @Override
    public List<Log> findAll() {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from logs")
                .mapToBean(Log.class).list());
    }

    public static void main(String[] args) {
        LogDAO logDAO = new LogDAOImp();
        var log = new Log();
        log.setLogLevel(Log_Level.INFO);
        log.setIp("192,15");
        log.setAddress("user");
        log.setBeforeValue("before");
        log.setAfterValue("after");
//        logDAO.add(log);
        logDAO.findAll().forEach(System.out::println);
    }

}
