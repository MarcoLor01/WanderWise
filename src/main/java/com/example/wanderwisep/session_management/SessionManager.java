package com.example.wanderwisep.session_management;

import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionManager {
    Logger logger = Logger.getLogger(SessionManager.class.getName());
    private final Map<String, Session> activeSessions;

    private SessionManager() {
        activeSessions = new HashMap<>();
    }

    private static class SingletonHelper {
        private static final SessionManager INSTANCE = new SessionManager();
    }


    public static SessionManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public String addSession(User user) {
        Session existingSession = checkDuplicateSession(user);
        if (existingSession != null) {
            logger.log(Level.WARNING, "Session already exists");
            return existingSession.getSessionId();
        }
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

    public Session checkDuplicateSession(User user) {
        return activeSessions.values()
                .stream()
                .filter(session -> session.getEmail().equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

}

