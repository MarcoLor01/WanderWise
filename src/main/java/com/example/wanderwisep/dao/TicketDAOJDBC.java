package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.DuplicateTourException;
import com.example.wanderwisep.exception.RequestNotFoundException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.model.Ticket;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketDAOJDBC implements TicketDAO {
    private static final Logger logger = Logger.getLogger(TicketDAOJDBC.class.getName());

    @Override
    public void createTicket(Ticket ticket) throws DAOException, SQLException, DuplicateTourException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement selectStmt = conn.prepareStatement(
                        Queries.ticketDuplicate,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                PreparedStatement insertStmt = conn.prepareStatement(
                        Queries.insertTicket,
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
            insertStmt.setDate(3, java.sql.Date.valueOf(ticket.getPrenotationDate()));
            insertStmt.setString(2, ticket.getState().getStateName());
            insertStmt.setString(4, ticket.getMyGuidedTourId());
            insertStmt.setString(5, ticket.getUser());
            insertStmt.setString(6, ticket.getMyTouristGuide());
            insertStmt.setDate(7, Date.valueOf(ticket.getDepartureDate()));
            insertStmt.setDate(8, Date.valueOf(ticket.getReturnDate()));
            insertStmt.setString(9, ticket.getTourName());

            int result = insertStmt.executeUpdate();

            if (result == 1) {
                logger.log(Level.INFO, "Ticket inserted");
            } else {
                throw new DAOException("Error creating ticket");
            }
        }
    }

    @Override
    public List<Ticket> retrieveTicket(String emailUser) throws SQLException, TicketNotFoundException {
        List<Ticket> ticketsUser = new ArrayList<>();

        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.retrieveTicket,
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
                    String myTouristGuide = rs.getString("TouristGuide");
                    LocalDate departureDate = rs.getDate("departureDate").toLocalDate();
                    LocalDate returnDate = rs.getDate("returnDate").toLocalDate();
                    String tourName = rs.getString("nameTour");
                    Ticket a = new Ticket(idTicket, state, prenotationDate, idTour, myTouristGuide, emailUser, departureDate, returnDate, tourName);
                    ticketsUser.add(a);
                } while (rs.next());
            }
        }

        return ticketsUser;
    }

    public List<Ticket> retrieveTicketForGuide(String touristGuide) throws SQLException, TicketNotFoundException {
        List<Ticket> ticketsUser = new ArrayList<>();

        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.retrievePrenotation,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, touristGuide);
            stmt.setString(2, stateEnum.toString(stateEnum.WAITING));

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.first()) {
                    throw new TicketNotFoundException("No request available");
                }

                rs.first();
                do {
                    String idTicket = rs.getString("idTicket");
                    String stateTicket = rs.getString("state");
                    LocalDate prenotationDate = rs.getDate("prenotationDate").toLocalDate();
                    String user = rs.getString("user");
                    String idTour = rs.getString("myGuidedTourId");
                    LocalDate departureDate = rs.getDate("departureDate").toLocalDate();
                    LocalDate returnDate = rs.getDate("returnDate").toLocalDate();
                    String nameTour = rs.getString("nameTour");
                    Ticket a = new Ticket(idTicket, stateEnum.fromString(stateTicket), prenotationDate, idTour, touristGuide, user, departureDate, returnDate, nameTour);
                    ticketsUser.add(a);
                } while (rs.next());
            }
        }

        return ticketsUser;
    }


    @Override
    public void retrieveTicketFromTourGuide(String touristGuide, String userEmail, String idTour, String decision) throws SQLException, RequestNotFoundException {
        try (
                Connection conn = new DBConnection().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        Queries.modifyTicketFromTourGuide,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            System.out.println(decision);
            System.out.println(touristGuide);
            System.out.println(userEmail);
            System.out.println(idTour);

            stmt.setString(1, decision);
            stmt.setString(2, touristGuide);
            stmt.setString(3, userEmail);
            stmt.setString(4, idTour);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new RequestNotFoundException("No request available");
            }
        }
    }

}

