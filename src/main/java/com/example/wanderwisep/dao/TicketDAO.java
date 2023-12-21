package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.model.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface TicketDAO {
    Integer createTicket(String tourName) throws DAOException, SQLException;

    List<Ticket> retrieveTicket(String touristGuideName);
}
