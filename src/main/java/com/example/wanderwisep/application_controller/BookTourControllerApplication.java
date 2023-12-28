package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.*;
import com.example.wanderwisep.dao.SearchTourDAO;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.DuplicateTourException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.pattern.TicketDAOFactory;
import com.example.wanderwisep.sessionmanagement.SessionManager;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.wanderwisep.application_controller.LoginControllerApplication.idSession;

public class BookTourControllerApplication {

    private static final Logger logger = Logger.getLogger(BookTourControllerApplication.class.getName());
    public BookTourControllerApplication() {
    }

    public GuidedTourBean getTourDescription(GuidedTourBean guidedTourBean) throws TourException, SQLException {
        SearchTourDAO searchTourDAO = new SearchTourDAO();
        GuidedTour myTour = searchTourDAO.retrieveTour(guidedTourBean.getTourName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());
        GuidedTourBean guidedTourB = new GuidedTourBean();
        guidedTourB.setCityName(myTour.getCityName());
        guidedTourB.setTourName(myTour.getNameTour());
        guidedTourB.setDepartureDate(myTour.getDepartureDate());
        guidedTourB.setReturnDate(myTour.getReturnDate());
        guidedTourB.setPhoto(myTour.getPhoto());
        guidedTourB.setListOfAttraction(myTour.getListOfAttraction());
        guidedTourB.setTouristGuideName(myTour.getMyTouristGuideName());
        guidedTourB.setTouristGuideSurname(myTour.getMyTouristGuideSurname());
        return guidedTourB;
    }

    public TourListBean searchTour(SearchBean searchBean) throws TourException, SQLException {
        SearchTourDAO searchTourDAO = new SearchTourDAO();
        List<GuidedTour> guidedTourList = searchTourDAO.findTours(searchBean.getCityName(), searchBean.getDepartureDate(), searchBean.getReturnDate());
        TourListBean tourListBean = new TourListBean();
        int dimensione = guidedTourList.size();
        int i = 0;
        while (i < dimensione) {
            tourListBean.setTourName(guidedTourList.get(i).getCityName(), i);
            tourListBean.setPhoto(guidedTourList.get(i).getPhoto(), i);
            tourListBean.setDepartureDate(guidedTourList.get(i).getDepartureDate(), i);
            tourListBean.setReturnDate(guidedTourList.get(i).getReturnDate(), i);
            i++;
        }
        return tourListBean;
    }

    public void createTicket(TicketBean ticketBean) throws IOException, DAOException, SQLException, DuplicateTourException, CsvValidationException {
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        String user = SessionManager.getInstance().getSession(idSession).getEmail();
        String idTicket = generateUniqueID(ticketBean.getGuidedTour(), ticketBean.getMyTouristGuide(), user);
        ticketDAOFactory.createTicketDAO().createTicket(idTicket, stateEnum.fromString(ticketBean.getStateTicket()), ticketBean.getPrenotationDate(), ticketBean.getGuidedTour(), ticketBean.getMyTouristGuide(), user);
        //Gestisci ora invio alla guida
    }

    public TicketListBean createMyArea(TicketListBean ticketListBean) throws IOException, TicketNotFoundException, SQLException {
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        List<Ticket> ticketList = ticketDAOFactory.createTicketDAO().retrieveTicket(ticketListBean.getEmail());
        int dimensione = ticketList.size();
        int i = 0;
        while (i < dimensione) {
            ticketListBean.setTourName(ticketList.get(i).getMyGuidedTour(), i);
            ticketListBean.setIdTicket(ticketList.get(i).getIdTicket(), i);
            ticketListBean.setEmail(ticketListBean.getEmail());
            ticketListBean.setPrenotationDate(ticketList.get(i).getPrenotationDate(), i);
            ticketListBean.setStateEnum(ticketList.get(i).getState(), i);
            i++;
        }
        return ticketListBean;
    }

    private String generateUniqueID(String myGuidedTour, String myTouristGuide, String user) {
        StringBuilder hexString = null;
        try {
            // Concatena i valori dei campi per formare una stringa univoca
            String uniqueString = myGuidedTour + myTouristGuide + user;
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