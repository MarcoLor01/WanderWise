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
        Statement stmt = null;
        Connection conn = null;
        List<GuidedTour> tours = new ArrayList<>();
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = Queries.findTour(stmt, city, Date.valueOf(departureDate), Date.valueOf(returnDate));
            if (!rs.first()) {
                throw new TourNotFoundException("No tours available");
            }
            rs.first();
            do {
                String tourName = rs.getString("nametour");
                Blob photoBlob = rs.getBlob("photo");
                GuidedTour a = new GuidedTour(tourName, photoBlob);
                tours.add(a);
            } while (rs.next());
            rs.close();
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
