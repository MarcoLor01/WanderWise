package com.example.wanderwisep.dao;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class TicketDAO {
    public abstract void createTicket(Ticket ticket) throws SQLException, IOException, DuplicateTourException, CsvValidationException, TourException, TouristGuideNotFoundException, DAOException;

    public abstract List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException, CsvValidationException, IOException, TourException, TouristGuideNotFoundException;

    public abstract void modifyTicketState(String userEmail, String idTour, String decision) throws SQLException, RequestNotFoundException, CsvValidationException, IOException;

    protected GuidedTour createTourGuide(String tourId) throws SQLException, TourException, TouristGuideNotFoundException {
        GuidedTourDAOJDBC guidedTourDAO = new GuidedTourDAOJDBC();
        return guidedTourDAO.retrieveTourFromId(tourId);
    }
}
