package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.stateEnum;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Ticket {
    Logger logger = Logger.getLogger(Ticket.class.getName());
    private String idTicket;
    private stateEnum state;
    private LocalDate prenotationDate;
    private String myGuidedTourId;
    private String user;
    private String myTouristGuide;
    private LocalDate departureDate;
    private LocalDate returnDate;
    private String tourName;


    public Ticket(stateEnum state, LocalDate prenotationDate, String myGuidedTourId, String myTouristGuide, String user, LocalDate departureDate, LocalDate returnDate, String tourName) {
        this.state = state;
        this.prenotationDate = prenotationDate;
        this.myGuidedTourId = myGuidedTourId;
        this.user = user;
        this.myTouristGuide = myTouristGuide;
        this.returnDate = returnDate;
        this.departureDate = departureDate;
        this.tourName = tourName;
    }

    public Ticket(String idTicket, stateEnum state, LocalDate prenotationDate, String myGuidedTourId, String myTouristGuide, String user, LocalDate departureDate, LocalDate returnDate, String tourName) {
        this.idTicket = idTicket;
        this.state = state;
        this.prenotationDate = prenotationDate;
        this.myGuidedTourId = myGuidedTourId;
        this.user = user;
        this.myTouristGuide = myTouristGuide;
        this.returnDate = returnDate;
        this.departureDate = departureDate;
        this.tourName = tourName;
    }

    public String getMyGuidedTourId() {
        return myGuidedTourId;
    }

    public void setMyGuidedTourId(String myGuidedTourId) {
        this.myGuidedTourId = myGuidedTourId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String myGuidedTourId, String myTouristGuide, String user) {
        this.idTicket = generateUniqueID(myGuidedTourId, myTouristGuide, user);
    }

    public stateEnum getState() {
        return state;
    }

    public void setState(stateEnum state) {
        this.state = state;
    }

    public LocalDate getPrenotationDate() {
        return prenotationDate;
    }

    public void setPrenotationDate(LocalDate prenotationDate) {
        this.prenotationDate = prenotationDate;
    }

    public void setMyGuidedTour(String myGuidedTourId) {
        this.myGuidedTourId = myGuidedTourId;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getMyTouristGuide() {
        return myTouristGuide;
    }

    public void setMyTouristGuide(String myTouristGuide) {
        this.myTouristGuide = myTouristGuide;
    }

    public String generateUniqueID(String myGuidedTourId, String myTouristGuide, String user) {
        StringBuilder hexString = null;
        try {
            // Concatena i valori dei campi per formare una stringa univoca
            String uniqueString = myGuidedTourId + myTouristGuide + user;
            // Calcola l'hash SHA-256 della stringa univoca
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(uniqueString.getBytes(StandardCharsets.UTF_8));
            // Converti l'array di byte in una rappresentazione esadecimale
            hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.INFO, e.getMessage());
        }
        // Restituisci l'ID univoco
        if (hexString != null) {
            return hexString.toString();
        } else {
            throw new NullPointerException("hexString is null");
        }
    }
}
