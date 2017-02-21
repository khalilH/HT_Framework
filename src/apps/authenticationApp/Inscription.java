package apps.authenticationApp;

import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class Inscription implements ApplicationInterface {

    @Override
    public Object doGet(RequestInterface request, SessionInterface session){
        // Ici on n'utilise pas de session

        String login, password;
        login = request.getParameter("login");
        password = request.getParameter("password");
        BDD.bd.put(login, password);
        return "true";

    }

}
