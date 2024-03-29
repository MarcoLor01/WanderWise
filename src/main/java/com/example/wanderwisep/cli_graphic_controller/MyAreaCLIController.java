package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.ManageMyBookedGuidedToursController;
import com.example.wanderwisep.bean.TicketListBean;
import com.example.wanderwisep.exception.TicketNotFoundException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.utilities.CLIPrinter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyAreaCLIController extends NavigatorCLIController {

    private final Logger logger = Logger.getLogger(MyAreaCLIController.class.getName());
    private final ManageMyBookedGuidedToursController manageMyGuidedTourApplication = new ManageMyBookedGuidedToursController();

    private String idSession;

    public void start(String sessionId) {
        try {
            idSession = sessionId;
            TicketListBean ticketListBean = new TicketListBean();
            ticketListBean.setIdSession(idSession);
            ticketListBean = manageMyGuidedTourApplication.createMyArea(ticketListBean);
            showMyTicket(ticketListBean);

        } catch (IOException | SQLException | CsvValidationException | TourNotFoundException |
                 TouristGuideNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (TicketNotFoundException e) {
            logger.log(Level.INFO, e.getMessage());
        }
    }

    private void showMyTicket(TicketListBean ticketListBean) {
        List<String> idTicket = ticketListBean.getIdTicket();
        List<String> stateEn = ticketListBean.getStateEnum();
        List<LocalDate> datePrenotation = ticketListBean.getPrenotationDate();
        List<String> tourName = ticketListBean.getTourName();
        CLIPrinter.printMessage("*** List of your tickets ***\n");
        int i;
        for (i = 0; i < tourName.size(); i++) {
            CLIPrinter.printMessage("Ticket Number: " + i + "\n");
            CLIPrinter.printMessage("Ticket Id: " + idTicket.get(i) + "\n");
            CLIPrinter.printMessage("Ticket State: " + stateEn.get(i) + "\n");
            CLIPrinter.printMessage("Prenotation Date: " + datePrenotation.get(i) + "\n");
            CLIPrinter.printMessage("Tour Name: " + tourName.get(i) + "\n\n");
        }
    }

}
