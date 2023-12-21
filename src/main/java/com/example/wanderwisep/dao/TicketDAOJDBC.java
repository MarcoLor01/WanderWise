package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketDAOJDBC implements TicketDAO {
    private static final Logger logger = Logger.getLogger(LoginUserDAO.class.getName());


    @Override
    public Integer createTicket(String tourName) throws DAOException, SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        Integer result = -1;
        LocalDate datePrenotation = LocalDate.now();
        String sql = "INSERT INTO ticket (myGuidedTour,prenotationDate, state, user) VALUES (?, ?, ?, ?)";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, tourName);
            stmt.setDate(2, java.sql.Date.valueOf(datePrenotation));
            result = stmt.executeUpdate();
            if (result == 1) {
                logger.log(Level.INFO, "Ticket inserted");
            } else {
                throw new DAOException("Error creating ticket");
            }
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            LoginGuideDAO.closeDAO(stmt, conn, logger);
        }
        return result;
    }

    @Override
    public List<Ticket> retrieveTicket(String touristGuideName) {
        List<Ticket> ticketsUser = null;

        return ticketsUser;
    }
}
