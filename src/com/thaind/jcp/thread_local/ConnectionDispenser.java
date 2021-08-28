package com.thaind.jcp.thread_local;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDispenser {
    private static String URL = "jdbc:mysql://localhost/test_database";

    public ThreadLocal<Connection> connectionHolder = new ThreadLocal(){
        @Override
        public Connection initialValue(){
            try{
                return DriverManager.getConnection(URL, "root", "");
            } catch(Exception ex){
                throw new RuntimeException("Unable to acquire Connection", ex);
            }
        }
    };

    public Connection getConnection(){
        return connectionHolder.get();
    }
}