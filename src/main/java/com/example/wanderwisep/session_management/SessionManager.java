package com.example.wanderwisep.session_management;

import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
public class SessionManager {
    private static SessionManager instance;
    private Map<String, Session> activeSessions;

    private SessionManager() {
        activeSessions = new HashMap<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String addSession(User user) {
        String sessionId = generateSessionId();
        Session session = new Session(user, sessionId);
        activeSessions.put(sessionId, session);
        return sessionId;
    }

    public String addSession(TouristGuide touristGuide) {
        String sessionId = generateSessionId();
        Session session = new Session(touristGuide, sessionId);
        activeSessions.put(sessionId, session);
        return sessionId;
    }

    public void removeSession(String idSession) {
        activeSessions.remove(idSession);
    }

    public Session getSession(String sessionId) {
        return activeSessions.get(sessionId);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }


}

