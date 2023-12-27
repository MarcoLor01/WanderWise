package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.DuplicateTourException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.model.Ticket;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TicketDAO {
    void createTicket(String tourName, String user, LocalDate prenotationDate, String stateTicket) throws DAOException, SQLException, IOException, DuplicateTourException;

    List<Ticket> retrieveTicket(String touristGuideName) throws SQLException, TicketNotFoundException;
}
