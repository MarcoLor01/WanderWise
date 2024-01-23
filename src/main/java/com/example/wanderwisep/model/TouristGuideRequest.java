package com.example.wanderwisep.model;

public class TouristGuideRequest {
    private String userEmail;
    private String touristGuideEmail;
    private String idTour;

    public TouristGuideRequest(String userEmail, String touristGuideEmail, String idTour) {
        this.userEmail = userEmail;
        this.touristGuideEmail = touristGuideEmail;
        this.idTour = idTour;
    }

    public String getIdTour() {
        return idTour;
    }

    public void setIdTour(String idTour) {
        this.idTour = idTour;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTouristGuideEmail() {
        return touristGuideEmail;
    }

    public void setTouristGuideEmail(String touristGuideEmail) {
        this.touristGuideEmail = touristGuideEmail;
    }
}
