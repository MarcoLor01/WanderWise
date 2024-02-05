package com.example.wanderwisep.dao;

import com.example.wanderwisep.exception.DuplicateTicketException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public abstract class TicketDAO {
    public abstract void createTicket(Ticket ticket) throws SQLException, IOException, DuplicateTicketException, CsvValidationException, TourNotFoundException, TouristGuideNotFoundException, TicketNotFoundException;

    public abstract List<Ticket> retrieveTicket(String userEmail) throws SQLException, TicketNotFoundException, CsvValidationException, IOException, TourNotFoundException, TouristGuideNotFoundException;

    public abstract void modifyTicketState(String userEmail, String idTour, String decision) throws SQLException, TicketNotFoundException, CsvValidationException, IOException;

    protected GuidedTour createGuidedTour(String tourId) throws SQLException, TourNotFoundException, TouristGuideNotFoundException {
        GuidedTourDAO guidedTourDAO = new GuidedTourDAOJDBC();
        return guidedTourDAO.retrieveTourFromId(tourId);
    }
}
