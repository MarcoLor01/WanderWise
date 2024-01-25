package com.example.wanderwisep.bean;

import java.io.Serializable;

public class EmailBean implements Serializable {

    private String touristGuideEmail;
    private String userEmail;
    private String guidedTourName;
    private String decision;

    public String getTouristGuideEmail() {
        return touristGuideEmail;
    }

    public void setTouristGuideEmail(String touristGuideEmail) {
        this.touristGuideEmail = touristGuideEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getGuidedTourName() {
        return guidedTourName;
    }

    public void setGuidedTourName(String guidedTourName) {
        this.guidedTourName = guidedTourName;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
