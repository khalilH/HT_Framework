package server;

import exception.MapperFileException;
import exception.MethodNotAllowedException;
import http.*;
import http.interfaces.ApplicationResponseInterface;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;
import http.interfaces.SessionInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Serveur HTTP
 */
public class HttpServer extends AbstractServer {

    /**
     * Routeur de requete
     */
    private Router router;

    /**
     * Constructeur du serveur HTTP, analyse syntaxiquement les fichier *.xml
     * pour initialiser le routage des requetes HTTP
     * @param port le port sur lequel ecoute le serveur
     * @throws MapperFileException si aucun fichier de mapping n'est presebt
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public HttpServer(int port) throws MapperFileException, IOException, SAXException, ParserConfigurationException {
        super(port);
        router = new Router();
        router.parseXML();
        router.route();
    }

    /**
     * Methode de traitement d'une requete HTTP
     *
     * @param request la requete a traiter
     * @return la reponse de la requete
     */
    @Override
    public ResponseInterface handleRequest(RequestInterface request) {

        // Recuperation du chemin de la requete
        Url url = request.getUrl();
        String path = url.getEntirePath();
        ResponseInterface response = null;

        // Saving the cookies
        String userHash = CookieTable.sha256(request.getIp()+request.getHeader(Headers.USER_AGENT));
        boolean hasUniqueIdCookie = false;
        String uniqueId = null;
        Cookie idCookie = null;

        // Recherche d'un cookie d'identification unique dans la requete
        for(Cookie cookie : request.getCookies()) {
            if(cookie.getKey().equals(CookieTable.UNIQUE_ID)) {
                hasUniqueIdCookie = true;
                uniqueId = cookie.getValue();
                request.setUniqueId(uniqueId);
                break;
            }
        }

        List<Cookie> userCookies;
        SessionInterface session = null;

        // Recherche de la classe service en fonction du chemin de la requete
        String className = "";
        for(String pattern : router.getPatterns()){
            if(Pattern.matches(pattern, path))
                className = router.getMapping(pattern);
        }

        // Gestion favicon.ico : requete ignoree pour le moment
        if (path.equals("/favicon.ico")) {
            response = new Response();
            response.setStatusCode(StatusCode.OK);
        } else {

            // Gestion cookie + session
            if(hasUniqueIdCookie) {
                if(CookieTable.cookieMap.containsKey(uniqueId)){
                    Cookie privateCookie = CookieTable.getUserCookies(uniqueId).get(0);
                    if(privateCookie.getValue().equals(userHash)) {
                        // On verifie que le cookie envoye par l'utilisateur correspond bien a
                        // la valeur hachee de son IP et son User-agent

                        // Recuperation de sa session a partir de son identifiant unique
                        SessionInterface _session = SessionTable.getUserSession(uniqueId);

                        // Si la session n'a pas expiree on l'a recupere
                        if (_session != null && _session.isAlive()) {
                            session = _session;
                        }
                    }else{
                        // Le cookie envoye ne correspond pas a la valeur hachee de l'IP et du user-agent
                        // on genere donc un nouveau cookie a l'utilisateur sans recuperer la session
                        // associee au cookie qu'il nous avait envoye
                        idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                        Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                        List<Cookie> cookieList = new ArrayList<>();
                        cookieList.add(0,hashCookie);
                        CookieTable.addCookiesToUser(idCookie.getValue(), cookieList);
                    }

                }else{
                    // Le cookie envoye n'est pas present dans la table de cookie
                    // on genere donc un nouveau cookie a l'utilisateur
                    idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                    List<Cookie> cookieList = new ArrayList<>();
                    Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                    cookieList.add(0,hashCookie);
                    CookieTable.addCookiesToUser(idCookie.getValue(), cookieList);
                }
            } else {
                // Le client n'a pas envoye de cookie avec son identifiant unique,
                // On lui en cree donc un, et on cree son cookie prive qui permettra de verfier
                // a chaque utilisation qu'il ne s'agit pas d'une usurpation
                idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                List<Cookie> requestCookiesClone = new ArrayList<>(request.getCookies());
                requestCookiesClone.add(0,hashCookie);
                CookieTable.addCookiesToUser(idCookie.getValue(), requestCookiesClone);
            }

            // Instanciation de la classe service
            try {
                Class methodClass = Class.forName(className);
                Object classInstance = methodClass.newInstance();
                String methodName = "";
                switch (request.getMethod()) {
                    case GET:
                        methodName = "doGet";
                        break;
                    case PUT:
                        methodName = "doPut";
                        break;
                    case POST:
                        methodName = "doPost";
                        break;
                    case DELETE:
                        methodName = "doDelete";
                        break;
                }

                Method method = methodClass.getMethod(methodName, RequestInterface.class, SessionInterface.class);

                ApplicationResponseInterface applicationResponse =
                        (ApplicationResponseInterface) method.invoke(classInstance, request, session);
                response = new Response();
                response.setStatusCode(StatusCode.OK);
                response.setBody(applicationResponse.getBody());
                response.addHeader(Headers.CONTENT_TYPE, applicationResponse.getContentType());
            } catch (ClassNotFoundException e) {
                response = new Response();
                response.setStatusCode(StatusCode.NOT_FOUND);
                //TODO faire avec la template
                response.setBody("<!DOCTYPE html><html><head><title>Halitran Framework v1.0- Rapport d''erreur</title><style type=\"text/css\">H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}.line {height: 1px; background-color: #525D76; border: none;}</style> </head><body><h1>Etat HTTP 404 - "+request.getUrl().getPath()+"</h1><div class=\"line\"></div><p><b>type</b> Rapport d''état</p><p><b>message</b> <u>"+request.getUrl().getPath()+"</u></p><p><b>description</b> <u>La ressource demandée n''est pas disponible.</u></p><hr class=\"line\"><h3>Halitran Framework v1.0</h3></body></html>");
                response.addHeader(Headers.CONTENT_TYPE, Headers.TEXT_HTML);
                response.addHeader(Headers.CONTENT_LENGTH, response.getBody().toString().length()+"");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
            } catch (InstantiationException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
            } catch (InvocationTargetException e) {
                e.printStackTrace(); // TODO logs
                if (e.getCause() instanceof MethodNotAllowedException) {
                    response = ResponseBuilder.serverResponse(StatusCode.METHOD_NOT_ALLOWED);
                } else {
                    System.out.println(e.getCause()); //TODO
                    e.printStackTrace();
                }
            } finally {
                if (response == null) {
                    response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR);
                }
            }
        }
        // Envoie du cookie de session au client
        if (idCookie != null) {
            response.addCookie(idCookie);
        }
        return response;
    }


    public static void main(String[] args) {
        try {
            HttpServer httpServer = new HttpServer(80);
            httpServer.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
