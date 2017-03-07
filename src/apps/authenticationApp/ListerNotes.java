package apps.authenticationApp;

import http.ApplicationResponse;
import http.Headers;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class ListerNotes implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        ApplicationResponseInterface response = new ApplicationResponse();
        Session s;
        if (session == null) {
            response.setBody("Vous n'avez pas de notes");
            return response;
        } else {
            s = (Session) session;
            String ret = "";
            for(String note : s.getNotes()) {
                ret += note+"<br/>";
            }
            response.setBody(ret);
            response.setContentType(Headers.TEXT_HTML);
            return response;
        }
    }





}
