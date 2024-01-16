package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.TourException;
import com.example.wanderwisep.utilities.CLIPrinter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TourListCLIController extends NavigatorCLIController {
    BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();
    Logger logger = Logger.getLogger(TourListCLIController.class.getName());
    private String idSession;

    public void start(TourListBean tourListBean) {
        try {
            idSession = tourListBean.getIdSession();
            int i;

            List<String> cityName = tourListBean.getTourName();
            List<LocalDate> departureDate = tourListBean.getDepartureDate();
            List<LocalDate> returnDate = tourListBean.getReturnDate();

            CLIPrinter.printMessage("*** Enter the number of the Guided Tour you want to book ***\n\n");

            for (i = 0; i < (tourListBean.getTourName()).size(); i++) {
                CLIPrinter.printMessage("Guided Tour number " + i + ":\n");
                CLIPrinter.printMessage("City: " + cityName.get(i) + "\n");
                CLIPrinter.printMessage("Departure Date: " + departureDate.get(i) + "\n");
                CLIPrinter.printMessage("Return Date: " + returnDate.get(i) + "\n\n");
            }
            CLIPrinter.printMessage("Insert here your choice: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            GuidedTourBean guidedTourBean = new GuidedTourBean();
            guidedTourBean.setIdSession(idSession);
            guidedTourBean.setTourName(cityName.get(choice));
            guidedTourBean.setDepartureDate(departureDate.get(choice));
            guidedTourBean.setReturnDate(returnDate.get(choice));
            guidedTourBean.setIdSession(idSession);
            guidedTourBean = bookTourControllerApplication.getTourDescription(guidedTourBean);
            new GuidedTourCLIController().start(guidedTourBean);
        } catch (TourException | SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
