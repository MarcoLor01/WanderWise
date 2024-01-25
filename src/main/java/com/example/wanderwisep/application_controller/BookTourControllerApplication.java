package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.*;
import com.example.wanderwisep.dao.GuidedTourDAOJDBC;
import com.example.wanderwisep.dao.TouristGuideDAO;
import com.example.wanderwisep.dao.TouristGuideRequestDAO;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.TouristGuideRequest;
import com.example.wanderwisep.pattern.TicketDAOFactory;
import com.example.wanderwisep.sessionmanagement.SessionManager;
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
        GuidedTourDAOJDBC guidedTourDAO = new GuidedTourDAOJDBC();
        List<GuidedTour> guidedTourList = guidedTourDAO.findTours(searchBean.getCityName(), searchBean.getDepartureDate(), searchBean.getReturnDate());
        TourListBean tourListBean = new TourListBean();
        tourListBean.setIdSession(searchBean.getIdSession());
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
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        ticketDAOFactory.createTicketDAO(CSV_DAO).createTicket(ticket);
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

    public void guideDecision(TouristGuideAnswerBean touristAnswerBean) throws IOException, SQLException, RequestNotFoundException, CsvValidationException, DAOException {
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        String touristGuideEmail = SessionManager.getInstance().getSession(touristAnswerBean.getIdSession()).getEmail();
        ticketDAOFactory.createTicketDAO(CSV_DAO).retrieveTicketFromTourGuide(touristAnswerBean.getUserEmail(), touristAnswerBean.getIdTour(), touristAnswerBean.getGuideDecision());
        TouristGuideRequestDAO touristGuideRequestDAO = new TouristGuideRequestDAO();
        touristGuideRequestDAO.deleteRequest(touristAnswerBean.getUserEmail(), touristGuideEmail, touristAnswerBean.getIdTour());
    }

    public TicketListBean createMyArea(TicketListBean ticketListBean) throws IOException, TicketNotFoundException, SQLException, CsvValidationException, TourException, TouristGuideNotFoundException {
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        String email = SessionManager.getInstance().getSession(ticketListBean.getIdSession()).getEmail();
        List<Ticket> ticketList = ticketDAOFactory.createTicketDAO(CSV_DAO).retrieveTicket(email);
        int dimensione = ticketList.size();
        int i = 0;
        while (i < dimensione) {
            ticketListBean.setIdTicket(ticketList.get(i).getIdTicket(), i);
            ticketListBean.setPrenotationDate(ticketList.get(i).getPrenotationDate(), i);
            ticketListBean.setStateEnum(ticketList.get(i).getState().getStateName(), i);
            ticketListBean.setTouristGuideName(ticketList.get(i).getMyGuidedTour().getTouristGuide().getName(), i);
            ticketListBean.setTourId(ticketList.get(i).getMyGuidedTour().getGuidedTourId(), i);
            ticketListBean.setTourName(ticketList.get(i).getMyGuidedTour().getNameTour(), i);
            ticketListBean.setDepartureDate(ticketList.get(i).getMyGuidedTour().getDepartureDate(), i);
            ticketListBean.setReturnDate(ticketList.get(i).getMyGuidedTour().getReturnDate(), i);
            i++;
        }
        return ticketListBean;
    }
}