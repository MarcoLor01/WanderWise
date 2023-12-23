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
    public int createTicket(String tourName, String user, LocalDate prenotationDate, String stateTicket) throws DAOException, SQLException {
        PreparedStatement selectStmt = null;
        PreparedStatement insertStmt = null;
        Connection conn = null;
        ResultSet resultSet = null;
        Integer result = -1;

        // Verifica se esistono già ticket con lo stesso nome e tourName
        String selectSql = "SELECT idTicket FROM ticket WHERE myGuidedTour = ? AND user = ?";

        // Inserisce il nuovo ticket se non esistono già
        String insertSql = "INSERT INTO ticket (myGuidedTour, prenotationDate, state, user) VALUES (?, ?, ?, ?)";

        try {
            conn = DBConnection.getConnection();

            // Esegui la query di selezione
            selectStmt = conn.prepareStatement(selectSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            selectStmt.setString(1, tourName);
            selectStmt.setString(2, user);
            resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                // Se esiste già un ticket con le stesse informazioni, gestisci questa situazione
                logger.log(Level.WARNING, "Ticket with the same name and user already exists");
                return -1;
            }

            // Esegui l'inserimento del nuovo ticket
            insertStmt = conn.prepareStatement(insertSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            insertStmt.setString(1, tourName);
            insertStmt.setDate(2, java.sql.Date.valueOf(prenotationDate));
            insertStmt.setString(3, stateTicket);
            insertStmt.setString(4, user);
            result = insertStmt.executeUpdate();

            if (result == 1) {
                logger.log(Level.INFO, "Ticket inserted");
            } else {
                throw new DAOException("Error creating ticket");
            }
        } finally {
            // Clean-up dell'ambiente
            LoginGuideDAO.closeDAO(selectStmt, null, logger); // Chiudi il result set associato alla query di selezione
            LoginGuideDAO.closeDAO(insertStmt, conn, logger);
        }

        return result;
    }

    @Override
    public List<Ticket> retrieveTicket(String touristGuideName) {
        List<Ticket> ticketsUser = null;

        return null;
    }
}

