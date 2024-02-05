package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.application_controller.LoginControllerApplication;
import com.example.wanderwisep.bean.LoginBean;
import com.example.wanderwisep.bean.TouristGuideAnswerBean;
import com.example.wanderwisep.bean.TouristGuideRequestsBean;
import com.example.wanderwisep.exception.*;
import com.example.wanderwisep.utilities.CLIPrinter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuideConfirmCLIController extends NavigatorCLIController {
    private String idSession;
    private final BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();
    private final Logger logger = Logger.getLogger(GuideConfirmCLIController.class.getName());

    public void start(String sessionId) {
        idSession = sessionId;
        boolean shouldExit = false;

        while (!shouldExit) {
            try {
                int choice = showMenuLogin();

                switch (choice) {
                    case 1 -> {
                        shouldExit = true;
                        showRequests();
                    }
                    case 2 -> {
                        return;
                    }
                    case 3 -> {
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

    private int showMenuLogin() {
        CLIPrinter.printMessage("*** Do you want to see the requests? ***\n");
        CLIPrinter.printMessage("1) Yes\n");
        CLIPrinter.printMessage("2) No\n");
        CLIPrinter.printMessage("3) Logout\n");
        return getMenuChoice(1, 3);
    }

    private void showRequests() {
        try {

            TouristGuideRequestsBean touristGuideAnswerBean = new TouristGuideRequestsBean();
            touristGuideAnswerBean.setIdSession(idSession);
            touristGuideAnswerBean = bookTourControllerApplication.createTouristGuideArea(touristGuideAnswerBean);
            List<String> userEmail = touristGuideAnswerBean.getUserEmail();
            List<String> guidedTourId = touristGuideAnswerBean.getGuidedTourId();
            CLIPrinter.printMessage("*** List of your Requests ***\nPress 1) For accept the request\nPress 2) For refuse the request\nPress 3) For see the next request\nPress 4) For exit\nPress 5) For logout\n");

            int i = 0;
            boolean shouldExit = false;
            while (i < guidedTourId.size()) {
                while (!shouldExit) {

                    int choice = showRequest(userEmail.get(i), guidedTourId.get(i), i);

                    switch (choice) {
                        case 1 -> {
                            shouldExit = true;
                            acceptRequest(userEmail.get(i), guidedTourId.get(i));
                            i++;
                        }
                        case 2 -> {
                            shouldExit = true;
                            refuseRequest(userEmail.get(i), guidedTourId.get(i));
                            i++;
                        }
                        case 3 -> {
                            shouldExit = true;
                            i++;
                        }
                        case 4 -> {
                            return;
                        }
                        case 5 -> {
                            shouldExit = true;
                            logout();
                        }

                        default -> throw new InvalidFormatException("Invalid choice");
                    }
                }
            }
            if (i == guidedTourId.size()) CLIPrinter.printMessage("There are no more requests\n");

        } catch (CsvValidationException | SQLException | IOException | InvalidFormatException | TourNotFoundException |
                 TouristGuideNotFoundException | TicketNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (RequestNotFoundException e) {
            logger.log(Level.SEVERE, "No request available", "tour confirmation");
        }
    }

    private int showRequest(String userEmail, String idTour, int i) {
        CLIPrinter.printMessage("*** Request number " + i + " ****\n");
        CLIPrinter.printMessage("User email: " + userEmail + "\n");
        CLIPrinter.printMessage("Guided Tour Id: " + idTour + "\n");
        return getMenuChoice(1, 5);
    }

    private void acceptRequest(String userEmail, String idTour) throws CsvValidationException, SQLException, RequestNotFoundException, IOException, TourNotFoundException, TouristGuideNotFoundException, TicketNotFoundException {
        TouristGuideAnswerBean answerBean = new TouristGuideAnswerBean();
        answerBean.setIdSession(idSession);
        answerBean.setIdTour(idTour);
        answerBean.setUserEmail(userEmail);
        answerBean.setGuideDecision("Confirmed");
        bookTourControllerApplication.guideDecision(answerBean);
        CLIPrinter.printMessage("\nRequest Accepted\n");
    }

    private void refuseRequest(String userEmail, String idTour) throws CsvValidationException, SQLException, RequestNotFoundException, IOException, TourNotFoundException, TouristGuideNotFoundException, TicketNotFoundException {
        TouristGuideAnswerBean answerBean = new TouristGuideAnswerBean();
        answerBean.setIdSession(idSession);
        answerBean.setIdTour(idTour);
        answerBean.setUserEmail(userEmail);
        answerBean.setGuideDecision("Refused");
        bookTourControllerApplication.guideDecision(answerBean);
        CLIPrinter.printMessage("\nRequest Refused\n");
    }

    public void logout() {
        LoginControllerApplication loginControllerApplication = new LoginControllerApplication();
        LoginBean loginBean = new LoginBean();
        loginBean.setIdSession(idSession);
        loginControllerApplication.logout(loginBean);
        new LoginCLIController().start();
    }

}
