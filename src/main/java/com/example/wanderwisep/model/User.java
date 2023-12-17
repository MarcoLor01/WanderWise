package com.example.wanderwisep.model;

import com.example.wanderwisep.enumeration.userRole;

import java.util.Vector;

public class User extends GenericUserProfile {
    private Vector<Ticket> myTicket;
    public User(String name, String surname, String email, userRole role) {
        super(name, surname, email, role);

        // Verifica che il tipo sia USER
        if (role != userRole.USER) {
            throw new IllegalArgumentException("Invalid user role. Expected USER.");
        }

        // Altri inizializzazioni del tuo costruttore
    }
}

