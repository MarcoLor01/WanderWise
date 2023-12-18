package com.example.wanderwisep.application_controller;
import com.example.wanderwisep.bean.SearchBean;
import com.example.wanderwisep.bean.TourListBean;
import com.example.wanderwisep.dao.SearchTourDAO;
import com.example.wanderwisep.exception.TourNotFoundException;
import com.example.wanderwisep.model.GuidedTour;

import java.sql.SQLException;
import java.util.List;

public class BookTourControllerApplication {

    public BookTourControllerApplication() {

    }
    public TourListBean searchTour(SearchBean searchBean) throws TourNotFoundException, SQLException {
        SearchTourDAO searchTourDAO = new SearchTourDAO();
        List<GuidedTour> guidedTourList = searchTourDAO.findTicket(searchBean.getCityName(), searchBean.getDepartureDate(), searchBean.getReturnDate());
        TourListBean tourListBean = new TourListBean();
        int dimensione = guidedTourList.size();
        int i = 0;
        while(i < dimensione){
            tourListBean.setTourName(guidedTourList.get(i).getCityName(),i);
            tourListBean.setPhoto(guidedTourList.get(i).getPhoto(),i);
            i++;
        }
        return tourListBean;
    }
 //   String attractionsString = rs.getString("listOfAttraction");
 //   attractionsString = attractionsString.replaceAll("[\\[\\]\"]", ""); Poi per il recupero delle varie attrazioni
    //   String[] attractionsArray = attractionsString.split(",");
 //   ArrayList<String> attractionsList = new ArrayList<>(Arrays.asList(attractionsArray));
}