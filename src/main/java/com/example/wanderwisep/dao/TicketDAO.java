package com.example.wanderwisep.dao;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.pattern.GuidedTourDAOFactory;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class TicketDAO {
    public abstract void createTicket(Ticket ticket) throws DAOException, SQLException, IOException, DuplicateTourException, CsvValidationException, TourException, TouristGuideNotFoundException;

    public abstract List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException, CsvValidationException, IOException, TourException, TouristGuideNotFoundException;

    public abstract void retrieveTicketFromTourGuide(String userEmail, String idTour, String decision) throws SQLException, RequestNotFoundException, CsvValidationException, IOException;

    protected GuidedTour createTourGuide(String tourId) throws SQLException, TourException, TouristGuideNotFoundException {
        GuidedTourDAOFactory guidedDAOFactory = new GuidedTourDAOFactory();
        return guidedDAOFactory.createGuidedTourDAO(1).retrieveTourFromId(tourId);
    }
}
