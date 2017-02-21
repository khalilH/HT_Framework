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
        return sessionMap.get(uniqueId);
    }

    public static void saveSession(String uniqueId, SessionInterface session) {
        sessionMap.put(uniqueId, session);
    }
}
