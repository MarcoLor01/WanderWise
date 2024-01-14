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
    private Connection connection;
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Se la connessione è nulla o chiusa, creane una nuova
            initializeConnection();
        }
        return connection;
    }
    private void initializeConnection() throws SQLException {
        try (InputStream propsInput = new FileInputStream("src/main/resources/com/example/wanderwisep/config.properties")) {
            Properties props = new Properties();
            props.load(propsInput);
            String dbUrl = props.getProperty("dbUrl");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (IOException e) {
            Logger logger = Logger.getLogger(DBConnection.class.getName());
            logger.log(Level.INFO, e.getMessage());
        }
    }

}
