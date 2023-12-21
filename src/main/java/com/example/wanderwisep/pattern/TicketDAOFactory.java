package com.example.wanderwisep.pattern;

import com.example.wanderwisep.dao.TicketDAO;
import com.example.wanderwisep.dao.TicketDAOCSV;
import com.example.wanderwisep.dao.TicketDAOJDBC;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TicketDAOFactory {

    public TicketDAO createCategoryDAO() throws IOException {
        InputStream input = new FileInputStream("config.properties");
        Properties properties = new Properties();
        properties.load(input);

        String categoryDaoType = properties.getProperty("CATEGORY_DAO_TYPE");

        switch (categoryDaoType) {
            case "jdbc":
                return new TicketDAOJDBC();
            case "csv":
                return new TicketDAOCSV();
            default:
                throw new IOException("Configuration file error");
        }
    }

    public TicketDAO createTicketDAOJDBC() {
        return new TicketDAOJDBC();
    }

    public TicketDAO createTicketDAOCSV() throws IOException {
        return new TicketDAOCSV();
    }
}


