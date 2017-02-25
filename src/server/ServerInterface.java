package server;

import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;

import java.io.IOException;

/**
 * Created by patrick.tran on 07/02/2017.
 */

public interface ServerInterface {

    /**
     * Permet de lancer le serveur
     * @throws IOException lorsque il y a un soucis lors de la lecture d'une requete
     */
    void start() throws IOException;

    /**
     * Permet de fermer le serveur
     * @throws IOException lorsqu'il y a un soucis lors de la fermeture du serveur
     */
    void shutdown() throws IOException;

    /**
     * Permet de traiter une requete HTTP
     * @param requestInterface l'objet contenant les informations de la requete
     * @return l'objet contenant la reponse associee au traitement de la requete
     */
    ResponseInterface handleRequest(RequestInterface requestInterface);
}
