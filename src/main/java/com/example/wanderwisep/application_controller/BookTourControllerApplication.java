package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.*;
import com.example.wanderwisep.boundary.EmailBookTourBoundary;
import com.example.wanderwisep.dao.*;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.model.TouristGuideRequest;
import com.example.wanderwisep.session_management.SessionManager;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookTourControllerApplication {

    public GuidedTourBean getTourDescription(GuidedTourBean guidedTourBean) throws TourNotFoundException, SQLException, TouristGuideNotFoundException {

        GuidedTourDAO searchTourDAO = new GuidedTourDAOJDBC();
        GuidedTour myTour = searchTourDAO.retrieveTour(guidedTourBean.getTourName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());

        //Setting bean
        GuidedTourBean guidedTourB = new GuidedTourBean();
        guidedTourB.setIdTour(myTour.getGuidedTourId());
        guidedTourB.setCityName(myTour.getCityName());
        guidedTourB.setTourName(myTour.getNameTour());
        guidedTourB.setDepartureDate(myTour.getDepartureDate());
        guidedTourB.setReturnDate(myTour.getReturnDate());
        guidedTourB.setPhoto(myTour.getPhoto());
        guidedTourB.setListOfAttraction(myTour.getListOfAttraction());
        guidedTourB.setTouristGuideName(myTour.getTouristGuide().getName());
        guidedTourB.setTouristGuideSurname(myTour.getTouristGuide().getSurname());

        //Saving the actual tour
        SessionManager.getInstance().getSession(guidedTourBean.getIdSession()).setActualGuidedTour(myTour);
        guidedTourB.setIdSession(guidedTourBean.getIdSession());
        return guidedTourB;
    }

    public TourListBean searchTour(SearchBean searchBean) throws TourNotFoundException, SQLException, IOException {

        String idSession = searchBean.getIdSession();

        GuidedTourDAO guidedTourDAO = new GuidedTourDAOJDBC();
        List<GuidedTour> guidedTourList = guidedTourDAO.findTours(searchBean.getCityName(), searchBean.getDepartureDate(), searchBean.getReturnDate());

        //Setting actual guided tour list
        SessionManager.getInstance().getSession(idSession).setGuidedTourList(guidedTourList);

        //Setting bean
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

    public void createTicket(TicketBean ticketBean) throws IOException, SQLException, DuplicateTicketException, CsvValidationException, TourNotFoundException, TouristGuideNotFoundException, TicketNotFoundException, RequestNotFoundException {
        String email = null;

        if (SessionManager.getInstance().getSession(ticketBean.getIdSession()).getRole().getRoleName().equals("User"))
            email = SessionManager.getInstance().getSession(ticketBean.getIdSession()).getEmail();

        GuidedTour tour = SessionManager.getInstance().getSession(ticketBean.getIdSession()).getActualGuidedTour();

        Ticket ticket = new Ticket(stateEnum.fromString(ticketBean.getStateTicket()), ticketBean.getPrenotationDate(), tour, email);
        ticket.setIdTicket(email);

        TicketDAOFactorySingleton ticketDAOFactory = TicketDAOFactorySingleton.getInstance();
        TicketDAO ticketDAO = ticketDAOFactory.createTicketDAO();
        ticketDAO.createTicket(ticket);

        //Create request for the tourist guide
        TouristGuideRequestDAO touristGuideDecisionDAO = new TouristGuideRequestDAO();
        touristGuideDecisionDAO.createRequest(email, tour.getTouristGuide().getEmail(), tour.getGuidedTourId());
    }

    public TouristGuideRequestsBean createTouristGuideArea(TouristGuideRequestsBean requestBean) throws SQLException, RequestNotFoundException {
        String email = null;

        if (SessionManager.getInstance().getSession(requestBean.getIdSession()).getRole().getRoleName().equals("TouristGuide"))
            email = SessionManager.getInstance().getSession(requestBean.getIdSession()).getEmail();

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

    public void guideDecision(TouristGuideAnswerBean touristAnswerBean) throws IOException, SQLException, RequestNotFoundException, CsvValidationException, TourNotFoundException, TouristGuideNotFoundException, TicketNotFoundException {
        String touristGuideEmail = null;

        TicketDAOFactorySingleton ticketDAOFactory = TicketDAOFactorySingleton.getInstance();

        if (SessionManager.getInstance().getSession(touristAnswerBean.getIdSession()).getRole().getRoleName().equals("TouristGuide"))
            touristGuideEmail = SessionManager.getInstance().getSession(touristAnswerBean.getIdSession()).getEmail();

        TicketDAO ticketDAO = ticketDAOFactory.createTicketDAO();

        //Setting ticket status
        ticketDAO.modifyTicketState(touristAnswerBean.getUserEmail(), touristAnswerBean.getIdTour(), touristAnswerBean.getGuideDecision());

        //Deleting accepted or refused request
        TouristGuideRequestDAO touristGuideRequestDAO = new TouristGuideRequestDAO();
        touristGuideRequestDAO.deleteRequest(touristAnswerBean.getUserEmail(), touristGuideEmail, touristAnswerBean.getIdTour());

        //Take the guided tour and send an email
        GuidedTourDAO guidedTourDAOJDBC = new GuidedTourDAOJDBC();
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