package com.example.wanderwisep.model;
import com.example.wanderwisep.enumeration.roleEnum;

import java.util.List;

public class User extends Person {

    private List<Ticket> myTicket;

    public User(String email, String name, String surname, roleEnum role) {
        super(email, name, surname, role);
    }

    public List<Ticket> getMyTicket() {
        return myTicket;
    }

    public void setMyTicket(List<Ticket> myTicket) {
        this.myTicket = myTicket;
    }

    
}

