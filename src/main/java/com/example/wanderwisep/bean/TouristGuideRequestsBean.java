package com.example.wanderwisep.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TouristGuideRequestsBean implements Serializable {
    private List<String> userEmail = new ArrayList<>();
    private List<String> guidedTourId = new ArrayList<>();
    private String idSession;

    public TouristGuideRequestsBean() {
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public List<String> getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail, Integer i) {
        (this.userEmail).add(i, userEmail);
    }

    public List<String> getGuidedTourId() {
        return guidedTourId;
    }

    public void setGuidedTourId(String guidedTourId, Integer i) {
        (this.guidedTourId).add(i, guidedTourId);
    }

}
