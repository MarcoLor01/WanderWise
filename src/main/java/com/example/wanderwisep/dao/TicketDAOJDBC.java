package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
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
        PreparedStatement selectStmt = null;
        PreparedStatement insertStmt = null;
        Connection conn;
        ResultSet resultSet;
        int result;
        System.out.println("IdTicket: " + ticket.getIdTicket());
        // Verifica se esistono già ticket con lo stesso nome e tourName
        String selectSql = "SELECT idTicket FROM ticket WHERE idTicket = ?";

        // Inserisce il nuovo ticket se non esistono già
        String insertSql = "INSERT INTO ticket (idTicket, state, prenotationDate, myGuidedTourId, user, touristGuide, departureDate, returnDate, nameTour) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            conn = DBConnection.getConnection();

            // Esegui la query di selezione
            selectStmt = conn.prepareStatement(selectSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            selectStmt.setString(1, ticket.getIdTicket());
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                throw new DuplicateTourException("Tour already booked");
            }
        } finally {
            if (selectStmt != null)
                selectStmt.close();
        }
        try {

            insertStmt = conn.prepareStatement(insertSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            insertStmt.setString(1, ticket.getIdTicket());
            insertStmt.setDate(3, java.sql.Date.valueOf(ticket.getPrenotationDate()));
            insertStmt.setString(2, ticket.getState().getStateName());
            insertStmt.setString(4, ticket.getMyGuidedTourId());
            insertStmt.setString(5, ticket.getUser());
            insertStmt.setString(6, ticket.getMyTouristGuide());
            insertStmt.setDate(7, Date.valueOf(ticket.getDepartureDate()));
            insertStmt.setDate(8, Date.valueOf(ticket.getReturnDate()));
            insertStmt.setString(9, ticket.getTourName());
            result = insertStmt.executeUpdate();

            if (result == 1) {
                logger.log(Level.INFO, "Ticket inserted");
            } else {
                throw new DAOException("Error creating ticket");
            }
        } finally {
            if (insertStmt != null)
                insertStmt.close();
            if (conn != null)
                conn.close();
        }
    }

    @Override
    public List<Ticket> retrieveTicket(String emailUser) throws SQLException, TicketNotFoundException {
        List<Ticket> ticketsUser = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM ticket WHERE user = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, emailUser);
            rs = stmt.executeQuery();
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
            rs.close();
        }
        return ticketsUser;
    }

    public List<Ticket> retrieveTicketForGuide(String touristGuide) throws SQLException, TicketNotFoundException {
        List<Ticket> ticketsUser = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM ticket WHERE touristGuide = ? AND state = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, touristGuide);
            stmt.setString(2, stateEnum.toString(stateEnum.WAITING));
            rs = stmt.executeQuery();
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
            rs.close();
        }
        return ticketsUser;
    }

    @Override
    public void retrieveTicketFromTourGuide(String touristGuide, String userEmail, String idTour, String decision) throws SQLException, RequestNotFoundException {
        String sql = "UPDATE ticket SET state = ? WHERE touristGuide = ? AND user = ? AND myGuidedTourId = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
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

