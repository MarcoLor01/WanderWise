package com.example.wanderwisep.dao;

import java.io.IOException;

public class TicketDAOFactorySingleton {

    private TicketDAOFactorySingleton() {

    }

    private static class SingletonHelper {
        private static final TicketDAOFactorySingleton INSTANCE = new TicketDAOFactorySingleton();

    }

    public static TicketDAOFactorySingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public TicketDAO createTicketDAO(int type) throws IllegalArgumentException, IOException {

        return switch (type) {
            case 1 -> new TicketDAOJDBC();
            case 2 -> new TicketDAOCSV();
            default -> throw new IllegalArgumentException("Invalid type : " + type);

        };
    }

    public TicketDAO createTicketDAOJDBC() {
        return new TicketDAOJDBC();
    }

    public TicketDAO createTicketDAOCSV() throws IOException {
        return new TicketDAOCSV();
    }

}


