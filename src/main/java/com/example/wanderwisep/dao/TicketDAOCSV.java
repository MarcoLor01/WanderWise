package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.model.Ticket;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TicketDAOCSV implements TicketDAO {


    @Override
    public int createTicket(String tourName, String user, LocalDate prenotationDate, String stateTicket) throws DAOException, SQLException {
        return 1;
    }

    @Override
    public List<Ticket> retrieveTicket(String touristGuideName) {
        return null;
    }
}
