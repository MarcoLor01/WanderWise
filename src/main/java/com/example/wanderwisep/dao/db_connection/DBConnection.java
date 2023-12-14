package com.example.wanderwisep.dao.db_connection;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
        private static Connection connection = null;

        public static Connection getConnection() throws IOException {
            if(connection == null){

                String dbUrl = "";
                String username = "";
                String password = "";
                FileInputStream propsInput = new FileInputStream("src/main/resources/config.properties");
                Properties props = new Properties();
                try{
                    props.load(propsInput);
                    dbUrl = props.getProperty("dbUrl");
                    username = props.getProperty("username");
                    password = props.getProperty("password");
                    String driverClassName = "com.mysql.jdbc.Driver";
                    Class.forName(driverClassName);
                    connection = DriverManager.getConnection(dbUrl, username, password);

                } catch (IOException | ClassNotFoundException | SQLException e) {
                    Logger logger = Logger.getLogger(DBConnection.class.getName());
                    logger.log(Level.WARNING, e.getMessage());
                }finally{
                    propsInput.close();
                }
            }
        return connection;
        }

}