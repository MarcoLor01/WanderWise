package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.model.GuidedTour;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SearchTourDAO {
    private static final Logger logger = Logger.getLogger(SearchTourDAO.class.getName());
    public List<GuidedTour> findTicket(String city, LocalDate departureDate, LocalDate returnDate) throws SQLException, TourNotFoundException{
        PreparedStatement stmt = null;
        Connection conn = null;
        List<GuidedTour> tours = new ArrayList<>();
        String sql = "SELECT * FROM guidedtour WHERE cityName = ? AND departureDate >= ? AND returnDate <= ?";
        try {
            conn = DBConnection.getConnection();
            stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            stmt.setString(1, city);
            stmt.setDate(2, Date.valueOf(departureDate));
            stmt.setDate(3, Date.valueOf(returnDate));
            ResultSet rs = stmt.executeQuery();
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
            LoginGuideDAO.closeDAO(stmt, conn, logger);
        }
            return tours;

        }
}
