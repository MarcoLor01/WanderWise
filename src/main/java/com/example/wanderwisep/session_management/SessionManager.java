package com.example.wanderwisep.session_management;

import com.example.wanderwisep.model.TouristGuide;
import com.example.wanderwisep.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionManager {
    private final Logger logger = Logger.getLogger(SessionManager.class.getName());
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
        Session existingSession = checkDuplicateSessionUser(user);
        if (existingSession != null) {
            logger.log(Level.WARNING, "Session already exists");
            return existingSession.getSessionId();
        }
        Session session = new Session(user);
        String sessionId = session.getSessionId();
        activeSessions.put(sessionId, session);
        return sessionId;
    }

    public String addSession(TouristGuide touristGuide) {
        Session existingSession = checkDuplicateSessionTouristGuide(touristGuide);
        if (existingSession != null) {
            logger.log(Level.WARNING, "Session already exists");
            return existingSession.getSessionId();
        }
        Session session = new Session(touristGuide);
        String sessionId = session.getSessionId();
        activeSessions.put(sessionId, session);
        return sessionId;
    }

    public void removeSession(String idSession) {
        activeSessions.remove(idSession);
    }

    public Session getSession(String sessionId) {
        return activeSessions.get(sessionId);
    }


    private Session checkDuplicateSessionUser(User user) {
        return activeSessions.values()
                .stream()
                .filter(session -> session.getEmail().equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    private Session checkDuplicateSessionTouristGuide(TouristGuide touristGuide) {
        return activeSessions.values()
                .stream()
                .filter(session -> session.getEmail().equals(touristGuide.getEmail()))
                .findFirst()
                .orElse(null);
    }

}

