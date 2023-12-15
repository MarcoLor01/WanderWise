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

    public static Connection getConnection() {
        return connection;
    }

    static {
        try (InputStream propsInput = new FileInputStream("src/main/resources/com/example/wanderwisep/config.properties")) {
            Properties props = new Properties();
            props.load(propsInput);
            String dbUrl = props.getProperty("dbUrl");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(dbUrl, username, password); //ERRORE QUA PORCO IL CRISTACCIO MALEDETTO
        } catch (IOException | SQLException e) {
            Logger logger = Logger.getLogger(DBConnection.class.getName());
            logger.log(Level.INFO, e.getMessage());
        }
    }
}
