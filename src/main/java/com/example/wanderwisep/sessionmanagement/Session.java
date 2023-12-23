package com.example.wanderwisep.sessionmanagement;

import java.util.UUID;

public class Session {
    String email;

    String sessionId;

    public Session(String email) {
        this.sessionId = generateSessionId();
        this.email = email;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }
}
