package http;

import http.interfaces.SessionInterface;

import java.util.HashMap;
import java.util.Map;

/**
 Classe representant la table des sessions
 */
public class SessionTable {

    /**
     * Table contenant les sessions associees au uniqueId d'un utilisateur d'une application
     */
    public static Map<String, Map<String, SessionInterface>> sessionMap;

    static {
        sessionMap = new HashMap<>();
    }

    /**
     * Permet de recuperer une session associee a un uniqueId
     * @param uniqueId l'identifiant unique associe a un utilisateur d'une application
     * @param appName L'application associee a la session
     * @return un objet SessionInterface
     */
    public static SessionInterface getUserSession(String uniqueId, String appName) {
        SessionInterface session = null;
        Map<String, SessionInterface> appMap = sessionMap.get(appName);
        if (appMap != null) {
            session = sessionMap.get(appName).get(uniqueId);
            if (session != null)
                session.updateLastAccess();
        }
        return session;
    }

    /**
     * Permet de sauvegarder la session d'un utilisateur
     * @param uniqueId l'identifiant unique associe a un utilisateur d'une application
     * @param session la session mise a jour
     * @param appName le nom de l'application liee a la session
     */
    public static void saveSession(String uniqueId, SessionInterface session, String appName) {
        if (sessionMap.get(appName) == null) {
            sessionMap.put(appName, new HashMap<>());
        }
        session.updateLastAccess();
        sessionMap.get(appName).put(uniqueId,session);
    }

    /**
     * Permet de supprimer une session
     * @param uniqueId l'identifiant unique associe a un utilisateur d'une application
     * @param appName le nom de l'application liee a la session
     */
    public static void deleteSession(String uniqueId, String appName) {
        sessionMap.get(appName).remove(uniqueId);
    }
}
