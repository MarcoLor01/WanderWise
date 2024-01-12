package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.*;
import com.example.wanderwisep.dao.SearchTourDAO;
import com.example.wanderwisep.enumeration.stateEnum;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.pattern.TicketDAOFactory;
import com.example.wanderwisep.sessionManagement.SessionManager;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookTourControllerApplication {
    public GuidedTourBean getTourDescription(GuidedTourBean guidedTourBean) throws TourException, SQLException {
        SearchTourDAO searchTourDAO = new SearchTourDAO();
        GuidedTour myTour = searchTourDAO.retrieveTour(guidedTourBean.getTourName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());
        GuidedTourBean guidedTourB = new GuidedTourBean();
        guidedTourB.setIdTour(myTour.getGuidedTourId());
        guidedTourB.setCityName(myTour.getCityName());
        guidedTourB.setTourName(myTour.getNameTour());
        guidedTourB.setDepartureDate(myTour.getDepartureDate());
        guidedTourB.setReturnDate(myTour.getReturnDate());
        guidedTourB.setPhoto(myTour.getPhoto());
        guidedTourB.setListOfAttraction(myTour.getListOfAttraction());
        guidedTourB.setTouristGuideName(myTour.getMyTouristGuideName());
        guidedTourB.setTouristGuideSurname(myTour.getMyTouristGuideSurname());
        SessionManager.getInstance().getSession(guidedTourBean.getIdSession()).setActualGuidedTour(myTour);
        guidedTourB.setIdSession(guidedTourBean.getIdSession());
        return guidedTourB;
    }

    public TourListBean searchTour(SearchBean searchBean) throws TourException, SQLException {
        SearchTourDAO searchTourDAO = new SearchTourDAO();
        List<GuidedTour> guidedTourList = searchTourDAO.findTours(searchBean.getCityName(), searchBean.getDepartureDate(), searchBean.getReturnDate());
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

    public void createTicket(TicketBean ticketBean) throws IOException, DAOException, SQLException, DuplicateTourException, CsvValidationException {
        String user = SessionManager.getInstance().getSession(ticketBean.getIdSession()).getEmail();
        GuidedTour tour = SessionManager.getInstance().getSession(ticketBean.getIdSession()).getActualGuidedTour();
        Ticket ticket = new Ticket(stateEnum.fromString(ticketBean.getStateTicket()), ticketBean.getPrenotationDate(), tour.getGuidedTourId(), tour.getMyTouristGuideName() + " " + tour.getMyTouristGuideSurname(), user, tour.getDepartureDate(), tour.getReturnDate(), tour.getNameTour());
        ticket.setIdTicket(tour.getGuidedTourId(), tour.getMyTouristGuideName() + tour.getMyTouristGuideSurname(), user);
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        ticketDAOFactory.createTicketDAO(1).createTicket(ticket);
    }

    public TouristGuideRequestsBean createTouristGuideArea(TouristGuideRequestsBean requestBean) throws IOException, TicketNotFoundException, SQLException {
        String name = SessionManager.getInstance().getSession(requestBean.getIdSession()).getName();
        String surname = SessionManager.getInstance().getSession(requestBean.getIdSession()).getSurname();
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        List<Ticket> tickets = ticketDAOFactory.createTicketDAO().retrieveTicketForGuide(name + " " + surname);
        int dimensione = tickets.size();
        int i = 0;
        while (i < dimensione) {
            requestBean.setGuidedTourId(tickets.get(i).getMyGuidedTour(), i);
            requestBean.setUserEmail(tickets.get(i).getUser(), i);
            i++;
        }
        return requestBean;
    }

    public void guideDecision(TouristGuideAnswerBean touristAnswerBean) throws IOException, SQLException, RequestNotFoundException {
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        String touristGuideName = SessionManager.getInstance().getSession(touristAnswerBean.getIdSession()).getName();
        String touristGuideSurname = SessionManager.getInstance().getSession(touristAnswerBean.getIdSession()).getSurname();
        ticketDAOFactory.createTicketDAO().retrieveTicketFromTourGuide(touristGuideName + " " + touristGuideSurname, touristAnswerBean.getUserEmail(), touristAnswerBean.getIdTour(), touristAnswerBean.getGuideDecision());
    }

    public TicketListBean createMyArea(TicketListBean ticketListBean) throws IOException, TicketNotFoundException, SQLException {
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        String email = SessionManager.getInstance().getSession(ticketListBean.getIdSession()).getEmail();
        List<Ticket> ticketList = ticketDAOFactory.createTicketDAO().retrieveTicket(email);
        int dimensione = ticketList.size();
        System.out.println("Dimensione: " + dimensione);
        int i = 0;
        while (i < dimensione) {
            ticketListBean.setIdTicket(ticketList.get(i).getIdTicket(), i);
            ticketListBean.setPrenotationDate(ticketList.get(i).getPrenotationDate(), i);
            ticketListBean.setStateEnum(ticketList.get(i).getState().getStateName(), i);
            ticketListBean.setTouristGuideName(ticketList.get(i).getMyTouristGuide(), i);
            ticketListBean.setTourId(ticketList.get(i).getMyGuidedTourId(), i);
            ticketListBean.setTourName(ticketList.get(i).getTourName(), i);
            ticketListBean.setDepartureDate(ticketList.get(i).getDepartureDate(), i);
            ticketListBean.setReturnDate(ticketList.get(i).getReturnDate(), i);
            i++;
        }
        return ticketListBean;
    }
}