package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.SearchBean;
import com.example.wanderwisep.bean.TicketBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.dao.SearchTourDAO;
import com.example.wanderwisep.exception.DAOException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.pattern.TicketDAOFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookTourControllerApplication {

    public BookTourControllerApplication() {
    }

    public GuidedTourBean getTourDescription(GuidedTourBean guidedTourBean) throws TourNotFoundException, SQLException {
        SearchTourDAO searchTourDAO = new SearchTourDAO();
        GuidedTour MyTour = searchTourDAO.retrieveTour(guidedTourBean.getTourName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());
        GuidedTourBean guidedTourB = new GuidedTourBean();
        guidedTourB.setCityName(MyTour.getCityName());
        guidedTourB.setTourName(MyTour.getNameTour());
        guidedTourB.setDepartureDate(MyTour.getDepartureDate());
        guidedTourB.setReturnDate(MyTour.getReturnDate());
        guidedTourB.setPhoto(MyTour.getPhoto());
        guidedTourB.setListOfAttraction(MyTour.getListOfAttraction());
        guidedTourB.setTouristGuideName(MyTour.getMyTouristGuide());
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

}