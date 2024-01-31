package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TicketBean;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.utilities.CLIPrinter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuidedTourCLIController extends NavigatorCLIController {
    private String idSession;

    private final BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();
    private final Logger logger = Logger.getLogger(GuidedTourCLIController.class.getName());

    public void start(GuidedTourBean guidedTourBean) {
        idSession = guidedTourBean.getIdSession();
        boolean shouldExit = false;
        CLIPrinter.printMessage("These are the details of the Guided Tour: \n");
        CLIPrinter.printMessage("Name of the Guided Tour: " + guidedTourBean.getTourName() + "\n");
        CLIPrinter.printMessage("Name of your Tourist Guide: " + guidedTourBean.getTouristGuideName() + " " + guidedTourBean.getTouristGuideSurname() + "\n");
        CLIPrinter.printMessage("list of attractions you will see during the guided tour: " + guidedTourBean.getListOfAttraction() + "\n");

        while (!shouldExit) {
            try {
                int choice = showMenu();
                switch (choice) {
                    case 1 -> {
                        shouldExit = true;
                        bookTour();
                    }
                    case 2 -> System.exit(0);
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (IOException | InvalidFormatException | DAOException | CsvValidationException | SQLException |
                     DuplicateTourException | TourException | TouristGuideNotFoundException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private int showMenu() {
        CLIPrinter.printMessage("*** You want To Book This Guided Tour? ***\n1) Book Guided Tour\n2) Exit\n");
        return getMenuChoice(1, 2);
    }

    private void bookTour() throws DAOException, CsvValidationException, SQLException, DuplicateTourException, IOException, TourException, TouristGuideNotFoundException {
        TicketBean ticketBean = new TicketBean();
        ticketBean.setPrenotationDate(LocalDate.now());
        ticketBean.setStateTicket("waiting for confirmation");
        ticketBean.setIdSession(idSession);
        bookTourControllerApplication.createTicket(ticketBean);
        CLIPrinter.printMessage("*** Tour Booked ***");
    }
}

