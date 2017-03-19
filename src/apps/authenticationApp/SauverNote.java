package apps.authenticationApp;

import http.ApplicationResponse;
import http.SessionManager;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class SauverNote implements ApplicationInterface {
    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        Session s;
        ApplicationResponseInterface response = new ApplicationResponse();
        String note = request.getParameter("note");
            if (session == null) {
                s = new Session();
                s.getNotes().add(note);
                SessionManager.save(request, s);
            } else {
                s = (Session) session;
                s.getNotes().add(note);
                SessionManager.save(request, s);
            }
        response.setBody(s.getNotes().size()+"");
        return response;
    }
}
