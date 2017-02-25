package http;

import http.interfaces.SessionInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ladislas on 21/02/2017.
 */
public class SessionTable {

    /**
     * Table contenant les sessions associees au uniqueId d'un utilisateur d'une application
     */
    public static Map<String, SessionInterface> sessionMap;

    static {
        sessionMap = new HashMap<>();
    }

    /**
     * Permet de recuperer une session associee a un uniqueId
     * @param uniqueId l'identifiant unique associe a un utilisateur d'une application
     * @return un objet SessionInterface
     */
    public static SessionInterface getUserSession(String uniqueId) {
        SessionInterface session = sessionMap.get(uniqueId);
        if (session != null)
            session.updateLastAccess();
        return session;
    }

    /**
     * Permet de sauvegarder la session d'un utilisateur
     * @param uniqueId l'identifiant unique associe a un utilisateur d'une application
     * @param session la session mise a jour
     */
    public static void saveSession(String uniqueId, SessionInterface session) {
        session.updateLastAccess();
        sessionMap.put(uniqueId, session);
    }
}
