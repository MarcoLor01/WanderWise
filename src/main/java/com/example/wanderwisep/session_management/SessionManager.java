package com.example.wanderwisep.session_management;

import com.example.wanderwisep.model.Person;

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

    public String addSession(Person person) {
        Session existingSession = checkDuplicateSessionPerson(person);
        if (existingSession != null) {
            logger.log(Level.WARNING, "Session already exists");
            return existingSession.getSessionId();
        }
        Session session = new Session(person);
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


    private Session checkDuplicateSessionPerson(Person person) {
        return activeSessions.values()
                .stream()
                .filter(session -> session.getEmail().equals(person.getEmail()))
                .findFirst()
                .orElse(null);
    }

}

