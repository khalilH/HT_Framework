package apps.authenticationApp;

import http.SessionManager;
import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ladislas on 21/02/2017.
 */
public class ListerNotes implements ApplicationInterface {

    @Override
    public Object doGet(RequestInterface request, SessionInterface session) {
        Session s;
        if (session == null) {
            return "Vous n'avez pas de notes";
        } else {
            s = (Session) session;
            String ret = "";
            for(String note : s.getNotes()) {
                ret += note+"<br>";
            }
            return ret;
        }
    }





}
