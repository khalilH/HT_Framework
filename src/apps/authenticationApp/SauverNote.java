package apps.authenticationApp;

import http.SessionManager;
import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class SauverNote implements ApplicationInterface {
    @Override
    public Object doGet(RequestInterface request, SessionInterface session) {
        Session s;
        String note = request.getParameter("note");
            if (session == null) {
                s = new Session();
                s.getNotes().add(note);
                SessionManager.save(request.getUniqueId(), s);
            } else {
                s = (Session) session;
                s.getNotes().add(note);
                SessionManager.save(request.getUniqueId(), s);
            }
            return s.getNotes().size()+"";
    }
}
