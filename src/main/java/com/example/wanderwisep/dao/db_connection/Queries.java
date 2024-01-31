package com.example.wanderwisep.dao.db_connection;

public class Queries {

    private Queries() {

    }

    public static final String RETRIEVE_REQUESTS_FOR_TOURIST_GUIDE =
            "SELECT * FROM request WHERE touristGuideEmail = ?";
    public static final String RETRIEVE_TOURIST_GUIDE =
            "SELECT * FROM touristguide WHERE email = ?";
    public static final String CREATE_REQUEST =
            "INSERT INTO request (tourId, userEmail, touristGuideEmail) VALUES (?,?,?)";
    public static final String RETRIEVE_TOUR_FROM_ID =
            "SELECT * FROM guidedtour WHERE idGuidedTour = ?";
    public static final String FIND_GUIDE =
            "SELECT * FROM touristguide WHERE email = ? AND password = ?";
    public static final String FIND_USER =
            "SELECT * FROM user WHERE email = ? AND password = ?";
    public static final String FIND_TOURS =
            "SELECT * FROM guidedtour WHERE cityName = ? AND departureDate >= ? AND returnDate <= ?";
    public static final String RETRIEVE_TOUR =
            "SELECT * FROM guidedtour WHERE nametour = ? AND departureDate = ? AND returnDate = ?";
    public static final String TICKET_DUPLICATE =
            "SELECT idTicket FROM ticket WHERE idTicket = ?";
    public static final String INSERT_TICKET =
            "INSERT INTO ticket (idTicket, state, prenotationDate, user, myGuidedTourId) VALUES (?,?,?,?,?)";
    public static final String RETRIEVE_TICKET =
            "SELECT * FROM ticket WHERE user = ?";
    public static final String MODIFY_TICKET_FROM_TOUR_GUIDE =
            "UPDATE ticket SET state = ? WHERE user = ? AND myGuidedTourId = ?";

    public static final String DELETE_REQUEST =
            "DELETE FROM REQUEST WHERE userEmail = ? AND touristGuideEmail = ? AND tourId = ?";
}
