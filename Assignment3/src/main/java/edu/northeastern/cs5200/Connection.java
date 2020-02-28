package edu.northeastern.cs5200;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://cs5200-spring2020-bao.c8jzluo6mljw.us-east-2.rds.amazonaws.com/JDBC?useSSL=false";
    private static final String USER = "yifan";
    private static final String PASSWORD = "byf8522479";
    private static java.sql.Connection dbConnection = null;


    public static java.sql.Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        if(dbConnection == null){
            dbConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            return dbConnection;
        }
        else{
            return dbConnection;
        }
    }
    public static void closeConnection(){
        try{
            if(dbConnection != null) {
                dbConnection.close();
                dbConnection = null;
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
