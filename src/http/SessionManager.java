package http;

import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class SessionManager {

    public static void save(String uniqueId, SessionInterface session){
        SessionTable.saveSession(uniqueId, session);
    }

}
