package apps.authenticationApp;

import http.ApplicationResponse;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class Inscription implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session){
        // Ici on n'utilise pas de session
        String login, password;
        ApplicationResponseInterface response = new ApplicationResponse();
        login = request.getParameter("login");
        password = request.getParameter("password");
        BDD.bd.put(login, password);
        response.setBody("true");
        return response;

    }

}
