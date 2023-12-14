package com.example.wanderwisep.sessionManagment;

public class Session {
    public Session(String username) {
        this.username = username;
    }

    public Integer getIdSession() {
        return idSession;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    private Integer idSession;
    private String username;
}
