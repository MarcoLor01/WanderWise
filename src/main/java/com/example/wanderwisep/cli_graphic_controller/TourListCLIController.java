package com.example.wanderwisep.cli_graphic_controller;

import com.example.wanderwisep.application_controller.BookTourControllerApplication;
import com.example.wanderwisep.bean.GuidedTourBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.exception.TouristGuideNotFoundException;
import com.example.wanderwisep.utilities.CLIPrinter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TourListCLIController extends NavigatorCLIController {
    private final BookTourControllerApplication bookTourControllerApplication = new BookTourControllerApplication();
    private final Logger logger = Logger.getLogger(TourListCLIController.class.getName());
    private String idSession;

    public void start(TourListBean tourListBean) {
        try {
            idSession = tourListBean.getIdSession();
            int i;

            List<String> cityName = tourListBean.getTourName();
            List<LocalDate> departureDate = tourListBean.getDepartureDate();
            List<LocalDate> returnDate = tourListBean.getReturnDate();

            CLIPrinter.printMessage("*** Enter the number of the Guided Tour you want to see details ***\n\n");

            int dimensione = (tourListBean.getTourName()).size();
            for (i = 0; i < dimensione; i++) {
                CLIPrinter.printMessage("Guided Tour number " + i + ":\n");
                CLIPrinter.printMessage("City: " + cityName.get(i) + "\n");
                CLIPrinter.printMessage("Departure Date: " + departureDate.get(i) + "\n");
                CLIPrinter.printMessage("Return Date: " + returnDate.get(i) + "\n\n");
            }
            int choice = getMenuChoice(0, dimensione);
            GuidedTourBean guidedTourBean = new GuidedTourBean();
            guidedTourBean.setIdSession(idSession);
            guidedTourBean.setTourName(cityName.get(choice));
            guidedTourBean.setDepartureDate(departureDate.get(choice));
            guidedTourBean.setReturnDate(returnDate.get(choice));
            guidedTourBean.setIdSession(idSession);
            guidedTourBean = bookTourControllerApplication.getTourDescription(guidedTourBean);
            new GuidedTourCLIController().start(guidedTourBean);

        } catch (TourNotFoundException | SQLException | TouristGuideNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
}
