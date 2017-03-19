package http;

import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Classe permettant au developpeur d'application de gerer les sessions
 */
public class SessionManager {

    /**
     * Permet de sauvegarder la session d'un utilisateur
     * @param request la requete d'origine
     * @param session la session mise a jour
     */
    public static void save(RequestInterface request, SessionInterface session){
        SessionTable.saveSession(request.getUniqueId(), session, request.getUrl().getAppName());
    }

    public static void delete(RequestInterface request) {
        SessionTable.deleteSession(request.getUniqueId(), request.getUrl().getAppName());
    }

}
