package com.example.wanderwisep.dao;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.DuplicateTourException;
import com.example.wanderwisep.exception.RequestNotFoundException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.model.Ticket;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface TicketDAO {
    void createTicket(Ticket ticket) throws DAOException, SQLException, IOException, DuplicateTourException, CsvValidationException;

    List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException;

    List<Ticket> retrieveTicketForGuide(String touristGuide) throws SQLException, TicketNotFoundException;

    void retrieveTicketFromTourGuide(String touristGuide, String userEmail, String idTour, String decision) throws SQLException, RequestNotFoundException;
}
