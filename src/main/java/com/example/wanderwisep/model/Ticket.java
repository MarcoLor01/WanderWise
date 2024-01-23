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
    private GuidedTour myGuidedTour;
    private String user;


    public Ticket(String idTicket, stateEnum state, LocalDate prenotationDate, GuidedTour tour, String user) {
        this.idTicket = idTicket;
        this.state = state;
        this.prenotationDate = prenotationDate;
        this.myGuidedTour = tour;
        this.user = user;
    }

    public Ticket(stateEnum state, LocalDate prenotationDate, GuidedTour tour, String user) {
        this.idTicket = idTicket;
        this.state = state;
        this.prenotationDate = prenotationDate;
        this.myGuidedTour = tour;
        this.user = user;
    }

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String user) {
        this.idTicket = generateUniqueID(user);
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

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public GuidedTour getMyGuidedTour() {
        return myGuidedTour;
    }

    public void setMyGuidedTour(GuidedTour myGuidedTour) {
        this.myGuidedTour = myGuidedTour;
    }

    public String generateUniqueID(String user) {
        StringBuilder hexString = null;
        try {
            // Concatena i valori dei campi per formare una stringa univoca
            String uniqueString = myGuidedTour.getGuidedTourId() + myGuidedTour.getTouristGuide().getName() + myGuidedTour.getTouristGuide().getSurname() + user;
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
