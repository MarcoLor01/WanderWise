package com.example.wanderwisep.dao;

import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.DuplicateTourException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.model.Ticket;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface TicketDAO {
    void createTicket(String idTicket, stateEnum state, LocalDate prenotationDate, String myGuidedTour, String myTouristGuide, String user) throws DAOException, SQLException, IOException, DuplicateTourException, CsvValidationException;

    List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException;

    List<Ticket> retrieveTicketForGuide(String touristGuide) throws SQLException, TicketNotFoundException;
}
