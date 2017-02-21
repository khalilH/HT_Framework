package apps.managerApp;

import http.interfaces.ApplicationInterface;
import http.interfaces.RequestInterface;

/**
    Classe repondant a une requete sans chemin : localhost
    on pourra renvoyer une page predefinie comnme un manuel dans une page html, autre chose
    ou bien faire ca aussi dans /help
 */
public class Manager implements ApplicationInterface {

    @Override
    public Object doGet(RequestInterface request) {
        String body = "Halitran Framework v1.0";
        return body;
    }
}