package com.example.wanderwisep.pattern;

import com.example.wanderwisep.dao.TicketDAO;
import com.example.wanderwisep.dao.TicketDAOCSV;
import com.example.wanderwisep.dao.TicketDAOJDBC;

import java.io.IOException;

public class TicketDAOFactory {

    public TicketDAO createTicketDAO(int type) throws IllegalArgumentException, IOException {

        return switch (type) {
            case 1 -> new TicketDAOJDBC();
            case 2 -> new TicketDAOCSV();
            default -> throw new IllegalArgumentException("Invalid type : " + type);
        };
    }

    public TicketDAO createTicketDAO() {
        return new TicketDAOJDBC();
    }

    public TicketDAO createTicketDAOJDBC() {
        return new TicketDAOJDBC();
    }

    public TicketDAO createTicketDAOCSV() throws IOException {
        return new TicketDAOCSV();
    }
}


