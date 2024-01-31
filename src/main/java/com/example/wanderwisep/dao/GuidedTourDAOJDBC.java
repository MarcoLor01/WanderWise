package com.example.wanderwisep.dao;

import com.example.wanderwisep.dao.db_connection.DBConnection;
import com.example.wanderwisep.dao.db_connection.Queries;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.TouristGuide;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GuidedTourDAOJDBC extends GuidedTourDAO {
    private static final String NAME_TOUR_ROW = "nametour";
    private static final String PHOTO_ROW = "photo";
    private static final String DEPARTURE_DATE_ROW = "departureDate";
    private static final String RETURN_DATE_ROW = "returnDate";
    private static final String ID_GUIDED_TOUR_ROW = "idGuidedTour";
    private static final String CITY_NAME_ROW = "cityName";
    private static final String LIST_OF_ATTRACTION_ROW = "listOfAttraction";
    private static final String TOURIST_GUIDE_ROW = "touristGuide";


    public List<GuidedTour> findTours(String city, LocalDate departureD, LocalDate returnD) throws SQLException, TourException {
        List<GuidedTour> guidedTourList = new ArrayList<>();
        Connection conn = DBConnection.getConnection();

        try (

                PreparedStatement stmt = conn.prepareStatement(
                        Queries.FIND_TOURS,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

        ) {
            stmt.setString(1, city);
            stmt.setDate(2, Date.valueOf(departureD));
            stmt.setDate(3, Date.valueOf(returnD));
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.first()) {
                    throw new TourException("No tours available");
                }
                do {
                    String tourName = rs.getString(NAME_TOUR_ROW);
                    Blob photoBlob = rs.getBlob(PHOTO_ROW);
                    LocalDate departure = rs.getDate(DEPARTURE_DATE_ROW).toLocalDate();
                    LocalDate dateReturn = rs.getDate(RETURN_DATE_ROW).toLocalDate();
                    String idTour = rs.getString(ID_GUIDED_TOUR_ROW);
                    GuidedTour a = new GuidedTour(tourName, photoBlob, departure, dateReturn, idTour);
                    guidedTourList.add(a);
                } while (rs.next());
            }
        }
        return guidedTourList;
    }

    public GuidedTour retrieveTour(String tourName, LocalDate departureDate, LocalDate returnDate) throws TourException, SQLException, TouristGuideNotFoundException {
        Connection conn = DBConnection.getConnection();
        try (

                PreparedStatement stmt = conn.prepareStatement(
                        Queries.RETRIEVE_TOUR,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, tourName);
            stmt.setDate(2, Date.valueOf(departureDate));
            stmt.setDate(3, Date.valueOf(returnDate));

            try (ResultSet rs = stmt.executeQuery()) {
                GuidedTour guidedTour;

                if (!rs.first()) {
                    throw new TourException("Error in tour retrieving");
                }

                rs.first();
                String idTour = rs.getString(ID_GUIDED_TOUR_ROW);
                String nameTour = rs.getString(NAME_TOUR_ROW);
                Blob photoBlob = rs.getBlob(PHOTO_ROW);
                LocalDate departureD = rs.getDate(DEPARTURE_DATE_ROW).toLocalDate();
                LocalDate returnD = rs.getDate(RETURN_DATE_ROW).toLocalDate();
                String cityName = rs.getString(CITY_NAME_ROW);
                String listOfAttraction = rs.getString(LIST_OF_ATTRACTION_ROW);
                String touristGuideEmail = rs.getString(TOURIST_GUIDE_ROW);
                List<String> attractionsArray = List.of(listOfAttraction.split(","));
                TouristGuide touristGuide = retrieveTouristGuide(touristGuideEmail);
                guidedTour = new GuidedTour(cityName, attractionsArray, departureD, returnD, touristGuide, nameTour, idTour);
                guidedTour.setPhoto(photoBlob);
                return guidedTour;
            }
        }
    }

    public GuidedTour retrieveTourFromId(String id) throws SQLException, TourException, TouristGuideNotFoundException {
        Connection conn = DBConnection.getConnection();
        try (

                PreparedStatement stmt = conn.prepareStatement(
                        Queries.RETRIEVE_TOUR_FROM_ID,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)
        ) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                GuidedTour guidedTour;

                if (!rs.first()) {
                    throw new TourException("Error in tour retrieving");
                }

                rs.first();
                String idTour = rs.getString(ID_GUIDED_TOUR_ROW);
                String nameTour = rs.getString(NAME_TOUR_ROW);
                Blob photoBlob = rs.getBlob(PHOTO_ROW);
                LocalDate departureD = rs.getDate(DEPARTURE_DATE_ROW).toLocalDate();
                LocalDate returnD = rs.getDate(RETURN_DATE_ROW).toLocalDate();
                String cityName = rs.getString(CITY_NAME_ROW);
                String listOfAttraction = rs.getString(LIST_OF_ATTRACTION_ROW);
                String touristGuideEmail = rs.getString(TOURIST_GUIDE_ROW);
                List<String> attractionsArray = List.of(listOfAttraction.split(","));
                TouristGuide touristGuide = retrieveTouristGuide(touristGuideEmail);
                guidedTour = new GuidedTour(cityName, attractionsArray, departureD, returnD, touristGuide, nameTour, idTour);
                guidedTour.setPhoto(photoBlob);
                return guidedTour;
            }
        }
    }
}
