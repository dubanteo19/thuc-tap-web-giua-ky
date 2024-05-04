package com.example.ltwgk.DAO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Log {
    private int id;
    private Log_Level logLevel;
    private String address;
    private String ip;
    private String beforeValue;
    private String afterValue;
    private Timestamp createAt;
}
