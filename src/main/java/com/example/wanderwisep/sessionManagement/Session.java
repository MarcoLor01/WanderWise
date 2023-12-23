package com.example.wanderwisep.sessionManagement;

import java.util.UUID;

public class Session {
    String email;

    String id;

    public Session(String email) {
        id = generateSessionId();
        this.email = email;
    }

    private String generateSessionId() {
        String id = UUID.randomUUID().toString();
        System.out.println("id sessione:");
        System.out.println(id);
        return id;
    }
}
