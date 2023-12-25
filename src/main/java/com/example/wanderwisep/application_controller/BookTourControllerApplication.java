package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.*;
import com.example.wanderwisep.dao.SearchTourDAO;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.pattern.TicketDAOFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookTourControllerApplication {

    public BookTourControllerApplication() {
    }

    public GuidedTourBean getTourDescription(GuidedTourBean guidedTourBean) throws TourNotFoundException, SQLException {
        SearchTourDAO searchTourDAO = new SearchTourDAO();
        GuidedTour myTour = searchTourDAO.retrieveTour(guidedTourBean.getTourName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());
        GuidedTourBean guidedTourB = new GuidedTourBean();
        guidedTourB.setCityName(myTour.getCityName());
        guidedTourB.setTourName(myTour.getNameTour());
        guidedTourB.setDepartureDate(myTour.getDepartureDate());
        guidedTourB.setReturnDate(myTour.getReturnDate());
        guidedTourB.setPhoto(myTour.getPhoto());
        guidedTourB.setListOfAttraction(myTour.getListOfAttraction());
        guidedTourB.setTouristGuideName(myTour.getMyTouristGuide());
        return guidedTourB;
    }
    public TourListBean searchTour(SearchBean searchBean) throws TourNotFoundException, SQLException {
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

    public TicketBean createTicket(TicketBean ticketBean) throws IOException, DAOException, SQLException {
        TicketDAOFactory ticketDAOFactory = new TicketDAOFactory();
        //SessionManagerSingleton.getInstance().getSession();
        int result = ticketDAOFactory.createTicketDAO().createTicket(ticketBean.getGuidedTour(), ticketBean.getEmailSender(), ticketBean.getPrenotationDate(), ticketBean.getStateTicket());
        ticketBean.setResult(result);
        return ticketBean;
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
}