package com.example.wanderwisep.sessionManagment;
import java.util.HashMap;
import java.util.Map;

    public class SessionManagerSingleton {
        private static SessionManagerSingleton instance;
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
        public Session getUserSession(String username) {
            if (!userSession.containsKey(username)) {
                // Se la sessione per l'utente non esiste, crea una nuova sessione
                Session nuovaSessione = new Session(username);
                userSession.put(username, nuovaSessione);
            }
            return userSession.get(username);
        }
}
