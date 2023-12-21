package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketDAOCSV implements TicketDAO {
    @Override
    public Integer createTicket(String tourName) throws DAOException, SQLException {
        return null;
    }

    @Override
    public List<Ticket> retrieveTicket(String touristGuideName) {
        return null;
    }
}
