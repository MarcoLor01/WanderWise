package com.example.wanderwisep.dao.db_connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static Connection connection;

    private DBConnection() {
    }

    static {
        try (InputStream input = new FileInputStream("src/main/resources/com/example/wanderwisep/config.properties")) {

            Properties properties = new Properties();
            properties.load(input);

            String connectionUrl = properties.getProperty("dbUrl");
            String user = properties.getProperty("username");
            String pass = properties.getProperty("password");

            connection = DriverManager.getConnection(connectionUrl, user, pass);
        } catch (IOException | SQLException e) {
            Logger logger = Logger.getLogger(DBConnection.class.getName());
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}




