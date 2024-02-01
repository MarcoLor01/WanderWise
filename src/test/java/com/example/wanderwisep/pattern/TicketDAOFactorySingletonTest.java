package com.example.wanderwisep.pattern;

import com.example.wanderwisep.dao.TicketDAO;
import com.example.wanderwisep.dao.TicketDAOFactorySingleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TicketDAOFactorySingletonTest {

    @Test
    void ticketDAOJDBCRight() {

        assertDoesNotThrow(() -> {
            TicketDAOFactorySingleton ticketDAOFactory = TicketDAOFactorySingleton.getInstance();
            TicketDAO ticketDAO = ticketDAOFactory.createTicketDAO(2);
        });
    }
}
