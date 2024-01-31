package com.example.wanderwisep.pattern;

import com.example.wanderwisep.dao.TicketDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TicketDAOFactoryTest {

    @Test
    void ticketDAOJDBCRight() {

        assertDoesNotThrow(() -> {
            TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
            TicketDAO ticketDAO = ticketDAOFactory.createTicketDAO(2);
        });
    }
}
