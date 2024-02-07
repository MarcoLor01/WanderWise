package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.bean.SearchBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.InvalidFormatException;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.utilities.CLIPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchBarCLIController extends NavigatorCLIController {
    private final Logger logger = Logger.getLogger(SearchBarCLIController.class.getName());
    private String idSession;
    private final BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();

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
                    case 2 -> {
                        return;
                    }
                    case 3 -> seeMyArea();
                    case 4 -> {
                        shouldExit = true;
                        logout();
                    }
                    default -> throw new InvalidFormatException("Invalid choice");
                }
            } catch (InvalidFormatException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private void seeMyArea() {
        new MyAreaCLIController().start(idSession);
    }

    private int insertTour() {

        CLIPrinter.printMessage("*** You want to look for a Guided Tour? ***\n");
        CLIPrinter.printMessage("1) Yes\n");
        CLIPrinter.printMessage("2) No\n");
        CLIPrinter.printMessage("3) See my Tickets\n");
        CLIPrinter.printMessage("4) Logout\n");
        return getMenuChoice(1, 4);
    }

    private void searchTour() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            SearchBean guidedTourBean = new SearchBean();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            CLIPrinter.printMessage("Please insert city name: ");
            String cityName = reader.readLine();
            guidedTourBean.setCityName(cityName);
            CLIPrinter.printMessage("\nPlease insert departure date in the format DD/MM/YYYY: ");
            String departureDate = reader.readLine();

            LocalDate departureD = LocalDate.parse(departureDate, formatter);

            guidedTourBean.setDepartureDate(departureD);
            CLIPrinter.printMessage("\nPlease insert return date in the format DD/MM/YYYY: ");
            String returnDate = reader.readLine();
            LocalDate returnD = LocalDate.parse(returnDate, formatter);
            guidedTourBean.setReturnDate(returnD);
            guidedTourBean.checkField(guidedTourBean.getCityName(), guidedTourBean.getDepartureDate(), guidedTourBean.getReturnDate());
            guidedTourBean.setIdSession(idSession);
            TourListBean tourListBean = bookTourControllerApplication.searchTour(guidedTourBean);
            new TourListCLIController().start(tourListBean);
        } catch (SQLException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (InvalidFormatException | TourNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Insert correct date", e);
        }
    }

    private void logout() {
        LoginControllerApplication loginControllerApplication = new LoginControllerApplication();
        LoginBean loginBean = new LoginBean();
        loginBean.setIdSession(idSession);
        loginControllerApplication.logout(loginBean);
        new LoginCLIController().start();
    }
}