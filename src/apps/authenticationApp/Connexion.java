package apps.authenticationApp;

import http.ApplicationResponse;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class Connexion implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session){

        ApplicationResponseInterface response = new ApplicationResponse();
        String login, password;

        login = request.getParameter("login");
        password = request.getParameter("password");
        String pw = BDD.bd.get(login);
        if(pw.equals(password)) {
            response.setBody("true");
        }else{
            response.setBody("false");
        }
        return response;
    }

}
