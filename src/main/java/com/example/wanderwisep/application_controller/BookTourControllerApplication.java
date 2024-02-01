package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.*;
import com.example.wanderwisep.boundary.EmailBookTourBoundary;
import com.example.wanderwisep.dao.*;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.TouristGuideRequest;
import com.example.wanderwisep.session_management.SessionManager;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookTourControllerApplication {

    private static final int JDBC_DAO = 1;
    private static final int CSV_DAO = 2;

    public GuidedTourBean getTourDescription(GuidedTourBean guidedTourBean) throws TourException, SQLException, TouristGuideNotFoundException {
        GuidedTourDAOJDBC searchTourDAO = new GuidedTourDAOJDBC();
        GuidedTour myTour = searchTourDAO.retrieveTour(guidedTourBean.getTourName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());
        TouristGuideDAO touristGuideDAO = new TouristGuideDAO();
        TouristGuide touristGuide = touristGuideDAO.retrieveTouristGuide(myTour.getTouristGuide().getEmail());
        myTour.setTouristGuide(touristGuide);
        GuidedTourBean guidedTourB = new GuidedTourBean();
        guidedTourB.setIdTour(myTour.getGuidedTourId());
        guidedTourB.setCityName(myTour.getCityName());
        guidedTourB.setTourName(myTour.getNameTour());
        guidedTourB.setDepartureDate(myTour.getDepartureDate());
        guidedTourB.setReturnDate(myTour.getReturnDate());
        guidedTourB.setPhoto(myTour.getPhoto());
        guidedTourB.setListOfAttraction(myTour.getListOfAttraction());
        guidedTourB.setTouristGuideName(touristGuide.getName());
        guidedTourB.setTouristGuideSurname(touristGuide.getSurname());
        SessionManager.getInstance().getSession(guidedTourBean.getIdSession()).setActualGuidedTour(myTour);
        guidedTourB.setIdSession(guidedTourBean.getIdSession());
        return guidedTourB;
    }

    public TourListBean searchTour(SearchBean searchBean) throws TourException, SQLException {
        String idSession = searchBean.getIdSession();
        GuidedTourDAOJDBC guidedTourDAO = new GuidedTourDAOJDBC();
        List<GuidedTour> guidedTourList = guidedTourDAO.findTours(searchBean.getCityName(), searchBean.getDepartureDate(), searchBean.getReturnDate());
        SessionManager.getInstance().getSession(idSession).setGuidedTourList(guidedTourList);
        TourListBean tourListBean = new TourListBean();
        tourListBean.setIdSession(idSession);
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

    public void createTicket(TicketBean ticketBean) throws IOException, DAOException, SQLException, DuplicateTourException, CsvValidationException, TourException, TouristGuideNotFoundException {
        String user = SessionManager.getInstance().getSession(ticketBean.getIdSession()).getEmail();
        GuidedTour tour = SessionManager.getInstance().getSession(ticketBean.getIdSession()).getActualGuidedTour();
        Ticket ticket = new Ticket(stateEnum.fromString(ticketBean.getStateTicket()), ticketBean.getPrenotationDate(), tour, user);
        ticket.setIdTicket(user);
        TicketDAOFactorySingleton ticketDAOFactory = TicketDAOFactorySingleton.getInstance();
        TicketDAO ticketDAO = ticketDAOFactory.createTicketDAO(JDBC_DAO);
        ticketDAO.createTicket(ticket);
        TouristGuideRequestDAO touristGuideDecisionDAO = new TouristGuideRequestDAO();
        touristGuideDecisionDAO.createRequest(user, tour.getTouristGuide().getEmail(), tour.getGuidedTourId());
    }

    public TouristGuideRequestsBean createTouristGuideArea(TouristGuideRequestsBean requestBean) throws SQLException, RequestNotFoundException {
        String email = SessionManager.getInstance().getSession(requestBean.getIdSession()).getEmail();
        TouristGuideRequestDAO touristGuideRequestDAO = new TouristGuideRequestDAO();
        List<TouristGuideRequest> requests = touristGuideRequestDAO.retrieveRequestsForGuide(email);
        int dimensione = requests.size();
        int i = 0;
        while (i < dimensione) {
            requestBean.setGuidedTourId(requests.get(i).getIdTour(), i);
            requestBean.setUserEmail(requests.get(i).getUserEmail(), i);
            i++;
        }
        return requestBean;
    }

    public void guideDecision(TouristGuideAnswerBean touristAnswerBean) throws IOException, SQLException, RequestNotFoundException, CsvValidationException, DAOException, TourException, TouristGuideNotFoundException {
        TicketDAOFactorySingleton ticketDAOFactory = TicketDAOFactorySingleton.getInstance();
        String touristGuideEmail = SessionManager.getInstance().getSession(touristAnswerBean.getIdSession()).getEmail();
        TicketDAO ticketDAO = ticketDAOFactory.createTicketDAO(JDBC_DAO);
        ticketDAO.modifyTicketState(touristAnswerBean.getUserEmail(), touristAnswerBean.getIdTour(), touristAnswerBean.getGuideDecision());
        TouristGuideRequestDAO touristGuideRequestDAO = new TouristGuideRequestDAO();
        touristGuideRequestDAO.deleteRequest(touristAnswerBean.getUserEmail(), touristGuideEmail, touristAnswerBean.getIdTour());
        GuidedTourDAOJDBC guidedTourDAOJDBC = new GuidedTourDAOJDBC();
        GuidedTour guidedTour = guidedTourDAOJDBC.retrieveTourFromId(touristAnswerBean.getIdTour());
        EmailBean emailBean = new EmailBean();
        emailBean.setDecision(touristAnswerBean.getGuideDecision());
        emailBean.setGuidedTourName(guidedTour.getNameTour());
        emailBean.setTouristGuideEmail(touristGuideEmail);
        emailBean.setUserEmail(touristAnswerBean.getUserEmail());
        EmailBookTourBoundary emailBookTourBoundary = new EmailBookTourBoundary();
        emailBookTourBoundary.initializeEmail(emailBean);
    }

    public TourListBean getMyTourList(String idSession) {
        TourListBean tourListBean = new TourListBean();
        List<GuidedTour> guidedTourList = SessionManager.getInstance().getSession(idSession).getGuidedTourList();
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
}