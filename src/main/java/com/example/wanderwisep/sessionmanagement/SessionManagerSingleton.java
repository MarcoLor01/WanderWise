//package com.example.wanderwisep.sessionmanagement;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class SessionManagerSingleton {
//    private static SessionManagerSingleton instance;
//    private Map<String, Session> activeSessions;
//
//    private SessionManagerSingleton() {
//        activeSessions = new HashMap<>();
//    }
//
//    public static synchronized SessionManagerSingleton getInstance() {
//        if (instance == null) {
//            instance = new SessionManagerSingleton();
//        }
//        return instance;
//    }
//
//    public String addSession(String email) {
//        String sessionId = generateSessionId();
//        Session session = new Session(email);
//        activeSessions.put(sessionId, session);
//        return sessionId;
//    }
//
//    public Session getSession(String sessionId) {
//        return activeSessions.get(sessionId);
//    }
//
//    private String generateSessionId() {
//        return UUID.randomUUID().toString();
//    }
//}
//
//