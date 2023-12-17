package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchTourDAO {
    private static final Logger logger = Logger.getLogger(SearchTourDAO.class.getName());
    public List<GuidedTour> findTicket(String city, LocalDate departureDate, LocalDate returnDate) throws SQLException, TourNotFoundException{
        PreparedStatement stmt = null;
        Connection conn = null;
        List<GuidedTour> tours = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM guidedtour WHERE cityName = ? AND departureDate >= ? AND returnDate <= ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, city);
            stmt.setDate(2, java.sql.Date.valueOf(departureDate));
            stmt.setDate(3, java.sql.Date.valueOf(returnDate));

            ResultSet rs = stmt.executeQuery();
            if (!rs.first()) {
                throw new TourNotFoundException("No tours available");
            }
            rs.first();
            do {
                // lettura delle colonne "by name"
                String tourName = rs.getString("nametour");
                Blob photoBlob = rs.getBlob("photo");
                GuidedTour a = new GuidedTour(tourName, photoBlob);
                tours.add(a);
            } while (rs.next());
        }finally{
            // STEP 5.2: Clean-up dell'ambiente
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                logger.log(Level.INFO, e.getMessage());
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                logger.log(Level.INFO, e.getMessage());
            }
        }
            return tours;

        }
}
