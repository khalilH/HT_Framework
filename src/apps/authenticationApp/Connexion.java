package apps.authenticationApp;

import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class Connexion implements ApplicationInterface {

    @Override
    public Object doGet(RequestInterface request, SessionInterface session){

        String login, password;
        login = request.getParameter("login");
        password = request.getParameter("password");
        String pw = BDD.bd.get(login);
        if(pw.equals(password)) {
            return "true";
        }else{
            return "false";
        }

    }

}
