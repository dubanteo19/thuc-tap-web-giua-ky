package com.example.ltwgk.DAO;

import java.util.List;

public interface LogDAO {
    int add(Log log);

    List<Log> findAll();

}
