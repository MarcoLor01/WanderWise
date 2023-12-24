package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.model.Ticket;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TicketDAO {
    int createTicket(String tourName, String user, LocalDate prenotationDate, String stateTicket) throws DAOException, SQLException;

    List<Ticket> retrieveTicket(String touristGuideName) throws SQLException, TicketNotFoundException;
}
