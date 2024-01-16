package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.SearchBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.utilities.CLIPrinter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchBarCLIController extends NavigatorCLIController {
    Logger logger = Logger.getLogger(SearchBarCLIController.class.getName());
    private String idSession;
    BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();

    public void start(String sessionId) {
        idSession = sessionId;
        boolean shouldExit = false;

        while (!shouldExit) {
            try {
                int choice = insertTour();

                switch (choice) {
                    case 1 -> {
                        shouldExit = true;
                        searchTour();
                    }
                    case 2 -> System.exit(0);
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }


    private int insertTour() {
        CLIPrinter.printMessage("*** You want to look for a Guided Tour? ***\n");
        CLIPrinter.printMessage("1) Yes\n");
        CLIPrinter.printMessage("2) No\n");

        return getMenuChoice(1, 2);
    }

    private void searchTour() {
        try {
            Scanner input = new Scanner(System.in);
            SearchBean guidedTourBean = new SearchBean();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            CLIPrinter.printMessage("Please insert city name: ");
            String cityName = input.nextLine();
            guidedTourBean.setCityName(cityName);
            CLIPrinter.printMessage("\nPlease insert departure date in the format DD/MM/YYYY: ");
            String departureDate = input.nextLine();
            LocalDate departureD = LocalDate.parse(departureDate, formatter);
            guidedTourBean.setDepartureDate(departureD);
            CLIPrinter.printMessage("\nPlease insert return date in the format DD/MM/YYYY: ");
            String returnDate = input.nextLine();
            LocalDate returnD = LocalDate.parse(returnDate, formatter);
            guidedTourBean.setReturnDate(returnD);
            guidedTourBean.checkField(guidedTourBean.getCityName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());
            guidedTourBean.setIdSession(idSession);
            TourListBean tourListBean = bookTourControllerApplication.searchTour(guidedTourBean);
            new TourListCLIController().start(tourListBean);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (InvalidFormatException | TourException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}