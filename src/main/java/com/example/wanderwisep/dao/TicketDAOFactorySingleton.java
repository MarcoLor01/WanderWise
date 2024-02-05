package com.example.wanderwisep.dao;

import java.io.IOException;
import java.time.LocalDate;

public class TicketDAOFactorySingleton {

    private TicketDAOFactorySingleton() {

    }

    private static class SingletonHelper {
        private static final TicketDAOFactorySingleton INSTANCE = new TicketDAOFactorySingleton();

    }

    public static TicketDAOFactorySingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public TicketDAO createTicketDAO() throws IllegalArgumentException, IOException {
        boolean factoryNumber = getPersistence();
        //if(factoryNumber) {
        //    return new TicketDAOJDBC();
        //}

        return new TicketDAOCSV();

    }

    public TicketDAO createTicketDAOJDBC() {
        return new TicketDAOJDBC();
    }

    public TicketDAO createTicketDAOCSV() throws IOException {
        return new TicketDAOCSV();
    }

    public static boolean getPersistence() {
        LocalDate date = LocalDate.now();
        int number = date.getDayOfMonth() % 2;
        return number == 1;
    }

}


