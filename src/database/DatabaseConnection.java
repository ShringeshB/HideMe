package database;

import java.sql.*;

public class DatabaseConnection {
    private Connection con;
    public Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hideme?characterEncoding=utf8", "root", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Database Connected");
        return con;
    }
}
