package com.example.wanderwisep.session_management;

import com.example.wanderwisep.enumeration.roleEnum;
import com.example.wanderwisep.model.GuidedTour;
import com.example.wanderwisep.model.Person;

import java.util.List;
import java.util.UUID;

public class Session {
    private String name;
    private String surname;
    private String email;
    private GuidedTour actualGuidedTour;
    private final String sessionId;
    private List<GuidedTour> guidedTourList;

    private roleEnum role;

    public Session(Person person) {
        this.sessionId = generateSessionId();
        this.name = person.getName();
        this.surname = person.getSurname();
        this.email = person.getEmail();
        this.role = person.getRole();
    }

    public List<GuidedTour> getGuidedTourList() {
        return guidedTourList;
    }

    public void setGuidedTourList(List<GuidedTour> guidedTourList) {
        this.guidedTourList = guidedTourList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public roleEnum getRole() {
        return role;
    }

    public void setRole(roleEnum role) {
        this.role = role;
    }

    public GuidedTour getActualGuidedTour() {
        return actualGuidedTour;
    }

    public void setActualGuidedTour(GuidedTour actualGuidedTour) {
        this.actualGuidedTour = actualGuidedTour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSessionId() {
        return sessionId;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

}
