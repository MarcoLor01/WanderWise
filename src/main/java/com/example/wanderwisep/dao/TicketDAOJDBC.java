package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.TicketNotFoundException;
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

public class TicketDAOJDBC implements TicketDAO {
    private static final Logger logger = Logger.getLogger(TicketDAOJDBC.class.getName());


    @Override
    public int createTicket(String tourName, String user, LocalDate prenotationDate, String stateTicket) throws DAOException, SQLException {
        PreparedStatement selectStmt = null;
        PreparedStatement insertStmt = null;
        Connection conn = null;
        ResultSet resultSet;
        int result;

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
    public List<Ticket> retrieveTicket(String emailUser) throws SQLException, TicketNotFoundException {
        List<Ticket> ticketsUser = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs;
        String sql = "SELECT * FROM ticket WHERE user = ?";

        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, emailUser);
            rs = stmt.executeQuery();
            if (!rs.first()) {
                throw new TicketNotFoundException("No ticket available");
            }
            rs.first();
            do {
                Integer idTicket = rs.getInt("idTicket");
                String stateTicket = rs.getString("state");
                java.util.Date prenotationDate = rs.getDate("prenotationDate");
                String tourName = rs.getString("myGuidedTour");
                stateEnum state = stateEnum.fromString(stateTicket);
                Ticket a = new Ticket(idTicket, state, prenotationDate, tourName, emailUser);
                ticketsUser.add(a);
            } while (rs.next());
            rs.close();
        } finally {
            // STEP 5.2: Clean-up dell'ambiente
            LoginGuideDAO.closeDAO(stmt, conn, logger);
        }
        return ticketsUser;
    }
}

