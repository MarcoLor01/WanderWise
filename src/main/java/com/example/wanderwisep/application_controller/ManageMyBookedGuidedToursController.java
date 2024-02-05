package com.example.wanderwisep.application_controller;

import com.example.wanderwisep.bean.TicketListBean;
import com.example.wanderwisep.dao.TicketDAO;
import com.example.wanderwisep.dao.TicketDAOFactorySingleton;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.model.Ticket;
import com.example.wanderwisep.session_management.SessionManager;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManageMyBookedGuidedToursController {


    public TicketListBean createMyArea(TicketListBean ticketListBean) throws IOException, TicketNotFoundException, SQLException, CsvValidationException, TourNotFoundException, TouristGuideNotFoundException {
        TicketDAOFactorySingleton ticketDAOFactory = TicketDAOFactorySingleton.getInstance();
        String email = SessionManager.getInstance().getSession(ticketListBean.getIdSession()).getEmail();
        TicketDAO ticketDAO = ticketDAOFactory.createTicketDAO();
        List<Ticket> ticketList = ticketDAO.retrieveTicket(email);

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
