package com.example.wanderwisep.dao.db_connection;

public class Queries {

    private Queries() {
    }

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
            "INSERT INTO ticket (idTicket, state, prenotationDate, myGuidedTourId, user, touristGuide, departureDate, returnDate, nameTour) VALUES (?,?,?,?,?,?,?,?,?)";
    public static final String RETRIEVE_TICKET =
            "SELECT * FROM ticket WHERE user = ?";
    public static final String RETRIEVE_PRENOTATION =
            "SELECT * FROM ticket WHERE touristGuide = ? AND state = ?";
    public static final String MODIFY_TICKET_FROM_TOUR_GUIDE = "UPDATE ticket SET state = ? WHERE touristGuide = ? AND user = ? AND myGuidedTourId = ?";
}
