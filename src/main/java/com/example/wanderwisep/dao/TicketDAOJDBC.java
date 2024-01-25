package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketDAOJDBC extends TicketDAO {
    Logger logger = Logger.getLogger(TicketDAOJDBC.class.getName());

    @Override
    public void createTicket(Ticket ticket) throws SQLException, DuplicateTourException, DAOException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement selectStmt = conn.prepareStatement(
                        Queries.TICKET_DUPLICATE,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                PreparedStatement insertStmt = conn.prepareStatement(
                        Queries.INSERT_TICKET,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {

            selectStmt.setString(1, ticket.getIdTicket());
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                if (resultSet.next()) {
                    throw new DuplicateTourException("Tour already booked");
                }
            }

            insertStmt.setString(1, ticket.getIdTicket());
            insertStmt.setString(2, ticket.getState().getStateName());
            insertStmt.setDate(3, java.sql.Date.valueOf(ticket.getPrenotationDate()));
            insertStmt.setString(4, ticket.getUser());
            insertStmt.setString(5, ticket.getMyGuidedTour().getGuidedTourId());
            int result = insertStmt.executeUpdate();

            if (result == 1) {
                logger.log(Level.INFO, "Ticket inserted");
            } else {
                throw new DAOException("Error creating ticket");
            }
        }
    }

    @Override
    public List<Ticket> retrieveTicket(String emailUser) throws SQLException, TicketNotFoundException, TourException, TouristGuideNotFoundException {
        List<Ticket> ticketsUser = new ArrayList<>();

        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.RETRIEVE_TICKET,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, emailUser);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.first()) {
                    throw new TicketNotFoundException("No ticket available");
                }

                rs.first();
                do {
                    String idTicket = rs.getString("idTicket");
                    String stateTicket = rs.getString("state");
                    LocalDate prenotationDate = rs.getDate("prenotationDate").toLocalDate();
                    String idTour = rs.getString("myGuidedTourId");
                    stateEnum state = stateEnum.fromString(stateTicket);
                    GuidedTour guidedTour = createTourGuide(idTour);
                    Ticket ticket = new Ticket(idTicket, state, prenotationDate, guidedTour, emailUser);
                    ticket.setIdTicket(emailUser);
                    ticketsUser.add(ticket);
                } while (rs.next());
            }
        }

        return ticketsUser;
    }


    @Override
    public void retrieveTicketFromTourGuide(String userEmail, String idTour, String decision) throws SQLException, RequestNotFoundException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.MODIFY_TICKET_FROM_TOUR_GUIDE,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {

            stmt.setString(1, decision);
            stmt.setString(2, userEmail);
            stmt.setString(3, idTour);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RequestNotFoundException("No request available");
            }
        }
    }

}

