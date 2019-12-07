package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private String url = "jdbc:postgresql://localhost/CONFLUENCE_USERS";
    private Properties props  = new Properties();
    private Connection conn;

    public static void main(String[] args) {
        DBConnection conn = new DBConnection();
        conn.initializeVariables();
    }
    private void initializeVariables() {
        props.setProperty("user", "postgres");
        props.setProperty("password", "12345");
        props.setProperty("ssl", "false");
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("I cant create connection for DB. Can you repair me plz?");
        }
    }

}
