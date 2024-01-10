package com.example.wanderwisep.bean;

import java.io.Serializable;

public class TouristGuideAnswerBean implements Serializable {

    private String idTour;
    private String userEmail;
    private String guideDecision;
    private String idSession;

    public TouristGuideAnswerBean() {
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
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

    public String getGuideDecision() {
        return guideDecision;
    }

    public void setGuideDecision(String guideDecision) {
        this.guideDecision = guideDecision;
    }
}
