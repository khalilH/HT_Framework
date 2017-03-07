package http;

import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */

/**
 * Classe permettant au developpeur d'application de gerer les sessions
 */
public class SessionManager {

    /**
     * Permet de sauvegarder la session d'un utilisateur
     * @param uniqueId l'identifiant unique associe a un utilisateur d'une application
     * @param session la session mise a jour
     */
    public static void save(String uniqueId, SessionInterface session){
        SessionTable.saveSession(uniqueId, session);
    }

    public static void delete(String uniqueId) {
        SessionTable.deleteSession(uniqueId);
    }

}
