package apps.managerApp;

import http.ApplicationResponse;
import http.interfaces.ApplicationInterface;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.SessionInterface;

/**
    Classe repondant a une requete sans chemin : localhost
    on pourra renvoyer une page predefinie comnme un manuel dans une page html, autre chose
    ou bien faire ca aussi dans /help
 */
public class Manager implements ApplicationInterface {

    @Override
    public ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) {
        String body = "Halitran Framework v1.0";
        ApplicationResponseInterface response = new ApplicationResponse();
        response.setBody(body);
        return response;
    }
}
