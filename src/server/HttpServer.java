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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Vrai serveur http qui fait le router puis execute la methode a appeler
 */
public class HttpServer extends AbstractServer {

    private Router router;
    public static String errorPageTemplatePath =  "src" + File.separator + "front" + File.separator + "ErrorPageTemplate.html";

    public HttpServer(int port) throws MapperFileException, IOException, SAXException, ParserConfigurationException {
        super(port);
        router = new Router();
        router.parseXML();
        router.route();
    }

    @Override
    public ResponseInterface handleRequest(RequestInterface request) {
        // Getting the class to instantiate depending on url path
        Url url = request.getUrl();
        String path = url.getEntirePath();
        String className = "";
        ResponseInterface response = null;
        Cookie idCookie = null;

        // Saving the cookies
        String userHash = CookieTable.sha256(request.getIp()+request.getHeader(Headers.USER_AGENT));
        boolean hasUniqueIdCookie = false;
        String uniqueId = null;
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

        for(String pattern : router.getPatterns()){
            if(Pattern.matches(pattern, path))
                className = router.getMapping(pattern);
        }

        // Gestion favicon.ico
        if (path.equals("/favicon.ico")) {
            response = new Response();
            response.setStatusCode(StatusCode.OK);
        } else {

            // Associating the cookies to the userUniqueId if needed

            if(hasUniqueIdCookie) {
                System.out.println("Cookie envoye par le client");
                if(CookieTable.cookieMap.containsKey(uniqueId)){

                    Cookie privateCookie = CookieTable.getUserCookies(uniqueId).get(0);
                    System.out.println("private cookie = "+privateCookie.toString());
                    if(privateCookie.getValue().equals(userHash)) {
                        System.out.println("Sending your sessoin");
                        SessionInterface _session = SessionTable.getUserSession(uniqueId);
                        if (_session != null && _session.isAlive()) {
                            session = _session;
                        }
                    }else{
                        System.out.println("Fucking hacker, emptying cookie list");
                        idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                        Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                        List<Cookie> cookieList = new ArrayList<>();
                        cookieList.add(0,hashCookie);
                        CookieTable.addCookiesToUser(idCookie.getValue(), cookieList);
                    }

                }else{
                    System.out.println("Fucking noob hacker, emptying cookie list");
                    idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                    Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                    List<Cookie> cookieList = new ArrayList<>();
                    cookieList.add(0,hashCookie);
                    CookieTable.addCookiesToUser(idCookie.getValue(), cookieList);
                }
            } else {
                System.out.println("le client n'a pas envoye de cookie avec son idUnique");
                idCookie = new Cookie(CookieTable.UNIQUE_ID, UUID.randomUUID().toString());
                Cookie hashCookie = new Cookie(CookieTable.USER_HASH, userHash);
                List<Cookie> requestCookiesClone = new ArrayList<>(request.getCookies());
                requestCookiesClone.add(0,hashCookie);
                CookieTable.addCookiesToUser(idCookie.getValue(), requestCookiesClone);
            }

            // Instantiating
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
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.NOT_FOUND, request.getUrl().getPath());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath());
            } catch (InstantiationException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                response = ResponseBuilder.serverResponse(StatusCode.NOT_IMPLEMENTED, request.getUrl().getPath());
            } catch (InvocationTargetException e) {
                e.printStackTrace(); // TODO logs
                if (e.getCause() instanceof MethodNotAllowedException) {
                    response = ResponseBuilder.serverResponse(StatusCode.METHOD_NOT_ALLOWED, request.getUrl().getPath());
                } else {
                    System.out.println(e.getCause()); //TODO
                    e.printStackTrace();
                }
            } finally {
                if (response == null) {
                    response = ResponseBuilder.serverResponse(StatusCode.INTERNAL_SERVER_ERROR, request.getUrl().getPath());
                }
            }
        }
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
