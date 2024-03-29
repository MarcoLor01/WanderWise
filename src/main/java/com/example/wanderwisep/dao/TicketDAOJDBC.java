package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.DuplicateTicketException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
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
    private final Logger logger = Logger.getLogger(TicketDAOJDBC.class.getName());

    @Override
    public void createTicket(Ticket ticket) throws SQLException, DuplicateTicketException, TicketNotFoundException {
        duplicateTicket(ticket.getIdTicket());
        Connection conn = DBConnection.getConnection();
        try (

                PreparedStatement insertStmt = conn.prepareStatement(
                        Queries.INSERT_TICKET,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {

            insertStmt.setString(1, ticket.getIdTicket());
            insertStmt.setString(2, ticket.getState().getStateName());
            insertStmt.setDate(3, java.sql.Date.valueOf(ticket.getPrenotationDate()));
            insertStmt.setString(4, ticket.getUser());
            insertStmt.setString(5, ticket.getMyGuidedTour().getGuidedTourId());
            int result = insertStmt.executeUpdate();

            if (result == 1) {
                logger.log(Level.INFO, "Ticket inserted");
            } else {
                throw new TicketNotFoundException("Error creating ticket");
            }
        }
    }

    private void duplicateTicket(String idTicket) throws SQLException, DuplicateTicketException {
        Connection conn = DBConnection.getConnection();
        try (

                PreparedStatement stmt = conn.prepareStatement(
                        Queries.TICKET_DUPLICATE,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

        ) {
            stmt.setString(1, idTicket);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    throw new DuplicateTicketException("Tour already booked");
                }
            }
        }
    }

    @Override
    public List<Ticket> retrieveTicket(String emailUser) throws TicketNotFoundException, TourNotFoundException, TouristGuideNotFoundException, SQLException {
        List<Ticket> ticketsUser = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        try (

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
                do {
                    String idTicket = rs.getString("idTicket");
                    String stateTicket = rs.getString("state");
                    LocalDate prenotationDate = rs.getDate("prenotationDate").toLocalDate();
                    String idTour = rs.getString("myGuidedTourId");
                    stateEnum state = stateEnum.fromString(stateTicket);
                    GuidedTour guidedTour = createGuidedTour(idTour);
                    Ticket ticket = new Ticket(idTicket, state, prenotationDate, guidedTour, emailUser);
                    ticket.setIdTicket(emailUser);
                    ticketsUser.add(ticket);
                } while (rs.next());
            }
        }
        return ticketsUser;
    }


    @Override
    public void modifyTicketState(String userEmail, String idTour, String decision) throws TicketNotFoundException, SQLException {
        Connection conn = DBConnection.getConnection();
        try (

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
                throw new TicketNotFoundException("No ticket available");
            }
        }
    }

}

