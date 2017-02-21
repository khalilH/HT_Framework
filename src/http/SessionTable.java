package http;

import http.interfaces.SessionInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ladislas on 21/02/2017.
 */
public class SessionTable {
    public static Map<String, SessionInterface> sessionMap;

    static {
        sessionMap = new HashMap<>();
    }

    public static SessionInterface getUserSession(String uniqueId) {
        SessionInterface session = sessionMap.get(uniqueId);
        session.updateLastAccess();
        return session;
    }

    public static void saveSession(String uniqueId, SessionInterface session) {
        session.updateLastAccess();
        sessionMap.put(uniqueId, session);
    }
}
