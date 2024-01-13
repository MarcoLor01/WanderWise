package com.example.wanderwisep.dao.db_connection;

public class Queries {

    public static final String findGuide =
            "SELECT * FROM touristguide WHERE email = ? AND password = ?";
    public static final String findUser =
            "SELECT * FROM user WHERE email = ? AND password = ?";
    public static final String findTours =
            "SELECT * FROM guidedtour WHERE cityName = ? AND departureDate >= ? AND returnDate <= ?";
    public static final String retrieveTour =
            "SELECT * FROM guidedtour WHERE nametour = ? AND departureDate = ? AND returnDate = ?";
    public static final String ticketDuplicate =
            "SELECT idTicket FROM ticket WHERE idTicket = ?";
    public static final String insertTicket =
            "INSERT INTO ticket (idTicket, state, prenotationDate, myGuidedTourId, user, touristGuide, departureDate, returnDate, nameTour) VALUES (?,?,?,?,?,?,?,?,?)";
    public static final String retrieveTicket =
            "SELECT * FROM ticket WHERE user = ?";
    public static final String retrievePrenotation =
            "SELECT * FROM ticket WHERE touristGuide = ? AND state = ?";
    public static final String modifyTicketFromTourGuide = "UPDATE ticket SET state = ? WHERE touristGuide = ? AND user = ? AND myGuidedTourId = ?";
}
