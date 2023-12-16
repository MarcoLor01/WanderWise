package com.example.wanderwisep.sessionManagment;
import java.util.HashMap;
import java.util.Map;

    public class SessionManagerSingleton {
        private static SessionManagerSingleton instance;
        private String currentUserEmail;
        private String currentUserName;
        private Map<String, Session> userSession;

        private SessionManagerSingleton() {
            userSession = new HashMap<>();
        }

        // Metodo per ottenere l'istanza singleton del SessionManager
        public static synchronized SessionManagerSingleton getInstance() {
            if (instance == null) {
                instance = new SessionManagerSingleton();
            }
            return instance;
        }

        // Metodo per ottenere una sessione per un utente
        public Session getUserSession(String email, String name) {
            setCurrentUser(email,name);
            return userSession.computeIfAbsent(email, Session::new);
        }
        public void setCurrentUser(String email, String name) {
            this.currentUserEmail = email;
            this.currentUserName = name;
        }

        public String getCurrentUserEmail() {
            return currentUserEmail;
        }

        public String getCurrentUserName() {
            return currentUserName;
        }

        public void clearCurrentUser() {
            this.currentUserEmail = null;
            this.currentUserName = null;
        }
    }

